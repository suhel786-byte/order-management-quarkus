package com.mintifi.ordermanagement.service;

import com.mintifi.ordermanagement.dto.request.CreateUserRequest;
import com.mintifi.ordermanagement.dto.response.UserResponse;
import com.mintifi.ordermanagement.entity.User;
import com.mintifi.ordermanagement.exception.UserAlreadyExistsException;
import com.mintifi.ordermanagement.exception.UserNotFoundException;
import com.mintifi.ordermanagement.mapper.UserMapper;
import com.mintifi.ordermanagement.repository.UserRepository;
import com.mintifi.ordermanagement.util.PasswordUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        User existingUser = userRepository.findByUsername(request.getUsername());

        if (existingUser != null) {
            throw new UserAlreadyExistsException(
                    "Username already exists");
        }
        User user = UserMapper.toEntity(request);

        user.setPassword(PasswordUtil.hashPassword(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.persist(user);
        return UserMapper.toResponse(user);
    }
    public UserResponse getUserById(Long id) {

        User user = userRepository.findUserById(id);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return UserMapper.toResponse(user);
    }
    public List<UserResponse> getAllUsers() {

        return userRepository.listAll().stream().map(UserMapper::toResponse).toList();
    }
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findUserById(id);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.delete(user);
    }
}