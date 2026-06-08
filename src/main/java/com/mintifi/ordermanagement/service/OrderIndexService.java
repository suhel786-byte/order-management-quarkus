package com.mintifi.ordermanagement.service;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mintifi.ordermanagement.document.OrderDocument;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramBucket;
import java.util.LinkedHashMap;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.mintifi.ordermanagement.entity.Order;
import com.mintifi.ordermanagement.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;



import java.util.List;

@ApplicationScoped
public class OrderIndexService {

    private static final String INDEX_NAME = "orders";

    @Inject
    ElasticsearchClient elasticsearchClient;

    @Inject
    OrderRepository orderRepository;

    @Inject
    RedisService redisService;

    @Inject
    ObjectMapper objectMapper;

    public void indexAllOrders() throws Exception {

        for (Order order : orderRepository.getAllOrders()) {

            OrderDocument document = new OrderDocument();

            document.setId(order.getId());
            document.setOrderNumber(order.getOrderNumber());
            document.setProductName(order.getProductName());
            document.setAmount(order.getAmount());
            document.setStatus(order.getStatus().name());
            document.setCustomerName(order.getCustomer().getName());
            document.setCreatedAt(order.getCreatedAt());

            IndexResponse response = elasticsearchClient.index(i -> i.index(INDEX_NAME).id(order.getId().toString()).document(document));
            System.out.println("Indexed order: " + response.id());
        }
    }
    public List<OrderDocument> search(
            String query,
            String status,
            int page,
            int size,
            String sortField)

            throws Exception {

        String cacheKey =
                "search:" +
                        query + ":" +
                        status + ":" +
                        page + ":" +
                        size + ":" +
                        sortField;

        String cachedResult =
                redisService.get(cacheKey);

        if (cachedResult != null) {

            System.out.println(
                    "CACHE HIT: " + cacheKey
            );

            return objectMapper.readValue(
                    cachedResult,
                    new TypeReference<List<OrderDocument>>() {}
            );
        }

        SearchResponse<OrderDocument> response =
                elasticsearchClient.search(s -> s
                                .index("orders")
                                .from(page * size)
                                .size(size)
                                .sort(sort -> sort
                                        .field(f -> f
                                                .field(sortField)
                                        )
                                )
                                .query(q -> q
                                        .bool(b -> b
                                                .must(m -> m
                                                        .multiMatch(mm -> mm
                                                                .query(query)
                                                                .fields(
                                                                        "productName",
                                                                        "customerName"
                                                                )
                                                        )
                                                )
                                                .filter(f -> f
                                                        .term(t -> t
                                                                .field("status.keyword")
                                                                .value(status)
                                                        )
                                                )
                                        )
                                ),
                        OrderDocument.class
                );

        List<OrderDocument> results =
                response.hits()
                        .hits()
                        .stream()
                        .map(hit -> hit.source())
                        .filter(java.util.Objects::nonNull)
                        .toList();

        redisService.put(
                cacheKey,
                results
        );

        System.out.println(
                "CACHE MISS: " + cacheKey
        );

        return results;
    }
    public Map<String, Long> getStatusStats() throws Exception {

        SearchResponse<Void> response =
                elasticsearchClient.search(s -> s
                                .index("orders")
                                .size(0)
                                .aggregations(
                                        "status_counts",
                                        a -> a.terms(
                                                t -> t.field(
                                                        "status.keyword"
                                                )
                                        )
                                ),
                        Void.class
                );

        Map<String, Long> result =
                new HashMap<>();

        for (StringTermsBucket bucket :
                response.aggregations()
                        .get("status_counts")
                        .sterms()
                        .buckets()
                        .array()) {

            result.put(
                    bucket.key().stringValue(),
                    bucket.docCount()
            );
        }

        return result;
    }
    public Map<String, Long> getProductStats() throws Exception {

        SearchResponse<Void> response =
                elasticsearchClient.search(s -> s
                                .index("orders")
                                .size(0)
                                .aggregations(
                                        "product_counts",
                                        a -> a.terms(
                                                t -> t.field(
                                                        "productName.keyword"
                                                )
                                        )
                                ),
                        Void.class
                );

        Map<String, Long> result =
                new HashMap<>();

        for (StringTermsBucket bucket :
                response.aggregations()
                        .get("product_counts")
                        .sterms()
                        .buckets()
                        .array()) {

            result.put(
                    bucket.key().stringValue(),
                    bucket.docCount()
            );
        }

        return result;
    }
    public Map<String, Double> getRevenueByStatus()
            throws Exception {

        SearchResponse<Void> response =
                elasticsearchClient.search(s -> s
                                .index("orders")
                                .size(0)
                                .aggregations(
                                        "status_buckets",
                                        a -> a
                                                .terms(t -> t
                                                        .field(
                                                                "status.keyword"
                                                        )
                                                )
                                                .aggregations(
                                                        "total_amount",
                                                        agg -> agg
                                                                .sum(sum -> sum
                                                                        .field(
                                                                                "amount"
                                                                        )
                                                                )
                                                )
                                ),
                        Void.class
                );

        Map<String, Double> result =
                new HashMap<>();

        for (StringTermsBucket bucket :
                response.aggregations()
                        .get("status_buckets")
                        .sterms()
                        .buckets()
                        .array()) {

            double revenue =
                    bucket.aggregations()
                            .get("total_amount")
                            .sum()
                            .value();

            result.put(
                    bucket.key().stringValue(),
                    revenue
            );
        }

        return result;
    }
    public Map<String, Double> getTopCustomersByRevenue()
            throws Exception {

        SearchResponse<Void> response =
                elasticsearchClient.search(s -> s
                                .index("orders")
                                .size(0)
                                .aggregations(
                                        "customer_buckets",
                                        a -> a
                                                .terms(t -> t
                                                        .field("customerName.keyword")
                                                        .size(10)
                                                )
                                                .aggregations(
                                                        "total_revenue",
                                                        agg -> agg
                                                                .sum(sum -> sum
                                                                        .field("amount")
                                                                )
                                                )
                                ),
                        Void.class
                );

        Map<String, Double> temp =
                new HashMap<>();

        for (StringTermsBucket bucket :
                response.aggregations()
                        .get("customer_buckets")
                        .sterms()
                        .buckets()
                        .array()) {

            double revenue =
                    bucket.aggregations()
                            .get("total_revenue")
                            .sum()
                            .value();

            temp.put(
                    bucket.key().stringValue(),
                    revenue
            );
        }

        return temp.entrySet()
                .stream()
                .sorted(
                        Map.Entry.<String, Double>comparingByValue()
                                .reversed()
                )
                .collect(
                        java.util.stream.Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> a,
                                java.util.LinkedHashMap::new
                        )
                );
    }
    public Map<String, Long> getOrdersPerDay()
            throws Exception {

        SearchResponse<Void> response =
                elasticsearchClient.search(s -> s
                                .index("orders")
                                .size(0)
                                .aggregations(
                                        "orders_per_day",
                                        a -> a
                                                .dateHistogram(h -> h
                                                        .field("createdAt")
                                                        .calendarInterval(
                                                                co.elastic.clients.elasticsearch._types.aggregations.CalendarInterval.Day
                                                        )
                                                )
                                ),
                        Void.class
                );

        Map<String, Long> result =
                new LinkedHashMap<>();

        for (DateHistogramBucket bucket :
                response.aggregations()
                        .get("orders_per_day")
                        .dateHistogram()
                        .buckets()
                        .array()) {

            result.put(
                    bucket.keyAsString(),
                    bucket.docCount()
            );
        }

        return result;
    }
}