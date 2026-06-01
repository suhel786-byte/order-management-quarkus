package com.mintifi.ordermanagement.repository;
import java.util.List;
import com.mintifi.ordermanagement.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public Customer findCustomerById(Long id) {
        return findById(id);
    }

    public Customer findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public List<Customer> getAllCustomers() {
        return listAll();
    }
}