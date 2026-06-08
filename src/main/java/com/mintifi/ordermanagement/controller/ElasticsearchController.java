package com.mintifi.ordermanagement.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.mintifi.ordermanagement.document.OrderDocument;
import com.mintifi.ordermanagement.service.OrderIndexService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;

import java.util.List;

@Path("/api/v1/elasticsearch")
@Produces(MediaType.TEXT_PLAIN)
public class ElasticsearchController {

    @Inject
    ElasticsearchClient elasticsearchClient;
    @Inject
    OrderIndexService orderIndexService;

    @GET
    @Path("/ping")
    public String ping() throws Exception {

        return "Connected to Elasticsearch "
                + elasticsearchClient.info()
                .version()
                .number();
    }

    @GET
    @Path("/index-orders")
    public String indexOrders() throws Exception {

        orderIndexService.indexAllOrders();

        return "Orders indexed successfully";
    }
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderDocument> search(
            @QueryParam("q") String query,
            @QueryParam("status") String status,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sort") @DefaultValue("amount") String sortField)
            throws Exception {
        return orderIndexService.search(
                query,
                status,
                page,
                size,
                sortField
        );

    }
    @GET
    @Path("/stats/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Long> statusStats()
            throws Exception {
        return orderIndexService.getStatusStats();
    }
    @GET
    @Path("/stats/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Long> productStats()
            throws Exception {

        return orderIndexService.getProductStats();
    }
    @GET
    @Path("/stats/revenue-by-status")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Double> revenueByStatus()
            throws Exception {

        return orderIndexService
                .getRevenueByStatus();
    }
    @GET
    @Path("/stats/top-customers")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Double> topCustomers()
            throws Exception {

        return orderIndexService
                .getTopCustomersByRevenue();
    }
    @GET
    @Path("/stats/orders-per-day")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Long> ordersPerDay()
            throws Exception {

        return orderIndexService.getOrdersPerDay();
    }
}