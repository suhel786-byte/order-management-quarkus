package com.mintifi.ordermanagement.controller;

import io.vertx.redis.client.RedisAPI;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/redis")
public class RedisController {

    @Inject
    RedisAPI redisAPI;

    @GET
    @Path("/ping")
    public String ping() {

        redisAPI.set(
                List.of(
                        "test-key",
                        "hello-redis"
                )
        );

        return "Redis Connected";
    }
}