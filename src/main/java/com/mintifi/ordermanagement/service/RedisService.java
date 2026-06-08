package com.mintifi.ordermanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.redis.client.RedisAPI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class RedisService {

    @Inject
    RedisAPI redisAPI;

    @Inject
    ObjectMapper objectMapper;

    public void put(String key, Object value)
            throws Exception {

        String json =
                objectMapper.writeValueAsString(value);

        redisAPI.set(
                        List.of(key, json)
                )
                .toCompletionStage()
                .toCompletableFuture()
                .get();

        var expireResponse =
                redisAPI.expire(
                                List.of(
                                        key,
                                        "300"
                                )
                        )
                        .toCompletionStage()
                        .toCompletableFuture()
                        .get();

        System.out.println(
                "EXPIRE RESPONSE = " + expireResponse
        );
    }
    public String get(String key)
            throws Exception {

        var response =
                redisAPI.get(key)
                        .toCompletionStage()
                        .toCompletableFuture()
                        .get();

        return response == null
                ? null
                : response.toString();
    }
}