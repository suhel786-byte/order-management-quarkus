package com.mintifi.ordermanagement.repository;

import com.mintifi.ordermanagement.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }
    public User findUserById(Long id){
        return findById(id);
    }
}