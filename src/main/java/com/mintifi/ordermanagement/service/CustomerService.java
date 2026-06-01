package com.mintifi.ordermanagement.service;

import com.mintifi.ordermanagement.dto.request.CreateCustomerRequest;
import com.mintifi.ordermanagement.dto.request.UpdateCustomerRequest;
import com.mintifi.ordermanagement.dto.response.CustomerResponse;
import com.mintifi.ordermanagement.entity.Customer;
import com.mintifi.ordermanagement.exception.CustomerAlreadyExistsException;
import com.mintifi.ordermanagement.exception.CustomerNotFoundException;
import com.mintifi.ordermanagement.mapper.CustomerMapper;
import com.mintifi.ordermanagement.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Customer existingCustomer =
                customerRepository.findByEmail(request.getEmail());

        if (existingCustomer != null) {
            throw new CustomerAlreadyExistsException("Email already exists");
        }
        Customer customer = CustomerMapper.toEntity(request);

        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        customerRepository.persist(customer);

        return CustomerMapper.toResponse(customer);
    }
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findCustomerById(id);

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        return CustomerMapper.toResponse(customer);
    }
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.getAllCustomers()
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }
    @Transactional
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {

        Customer customer =
                customerRepository.findCustomerById(id);

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setUpdatedAt(LocalDateTime.now());

        return CustomerMapper.toResponse(customer);
    }
    @Transactional
    public void deleteCustomer(Long id) {

        Customer customer =
                customerRepository.findCustomerById(id);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        customerRepository.delete(customer);
    }
}