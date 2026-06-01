package com.mintifi.ordermanagement.mapper;

import com.mintifi.ordermanagement.dto.request.CreateCustomerRequest;
import com.mintifi.ordermanagement.dto.response.CustomerResponse;
import com.mintifi.ordermanagement.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CreateCustomerRequest request) {

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        return customer;
    }

    public static CustomerResponse toResponse(Customer customer) {

        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }
}