package com.mintifi.ordermanagement.mapper;

import com.mintifi.ordermanagement.dto.response.OrderResponse;
import com.mintifi.ordermanagement.entity.Order;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setAmount(order.getAmount());
        response.setStatus(order.getStatus().name());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());
        response.setProductName(order.getProductName());

        return response;
    }
}