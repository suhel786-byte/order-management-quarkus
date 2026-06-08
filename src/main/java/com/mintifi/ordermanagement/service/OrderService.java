package com.mintifi.ordermanagement.service;

import com.mintifi.ordermanagement.dto.request.CreateOrderRequest;
import com.mintifi.ordermanagement.dto.request.UpdateOrderStatusRequest;
import com.mintifi.ordermanagement.dto.response.OrderResponse;
import com.mintifi.ordermanagement.entity.Customer;
import com.mintifi.ordermanagement.entity.Order;
import com.mintifi.ordermanagement.enums.OrderStatus;
import com.mintifi.ordermanagement.exception.CustomerNotFoundException;
import com.mintifi.ordermanagement.exception.OrderNotFoundException;
import com.mintifi.ordermanagement.mapper.OrderMapper;
import com.mintifi.ordermanagement.repository.CustomerRepository;
import com.mintifi.ordermanagement.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    CustomerRepository customerRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        Customer customer =
                customerRepository.findCustomerById(request.getCustomerId());

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        Order existingOrder =
                orderRepository.findByOrderNumber(request.getOrderNumber());

        if (existingOrder != null) {
            throw new RuntimeException("Order number already exists");
        }

        Order order = new Order();

        order.setCustomer(customer);
        order.setOrderNumber(request.getOrderNumber());
        order.setAmount(request.getAmount());
        order.setProductName(request.getProductName());
        try {
            order.setStatus(
                    OrderStatus.valueOf(request.getStatus())
            );
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid order status");
        }
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.persist(order);

        return OrderMapper.toResponse(order);
    }
    public OrderResponse getOrderById(Long id) {

        Order order = orderRepository.findOrderById(id);

        if (order == null) {
            throw new OrderNotFoundException("Order not found");
        }

        return OrderMapper.toResponse(order);
    }
    public List<OrderResponse> getAllOrders() {

        return orderRepository.getAllOrders().stream().map(OrderMapper::toResponse).toList();
    }
    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {

        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        return orderRepository.findByCustomerId(customerId).stream().map(OrderMapper::toResponse).toList();
    }
    @Transactional
    public OrderResponse updateOrderStatus(Long id, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findOrderById(id);

        if (order == null) {
            throw new OrderNotFoundException("Order not found");
        }
        order.setStatus(OrderStatus.valueOf(request.getStatus()));
        order.setUpdatedAt(LocalDateTime.now());

        return OrderMapper.toResponse(order);
    }
    public List<OrderResponse> getAllOrders(int page, int size) {

        return orderRepository.getAllOrders(page, size).stream().map(OrderMapper::toResponse).toList();
    }
}