package com.mintifi.ordermanagement.repository;

import com.mintifi.ordermanagement.entity.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.panache.common.Page;

import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public Order findByOrderNumber(String orderNumber) {
        return find("orderNumber", orderNumber).firstResult();
    }
    public Order findOrderById(Long id) {
        return findById(id);
    }
    public List<Order> getAllOrders() {
        return listAll();
    }
    public List<Order> findByCustomerId(Long customerId) {
        return find("customer.id", customerId).list();
    }
    public List<Order> getAllOrders(int page, int size) {
        return findAll().page(Page.of(page, size)).list();
    }
}