package com.mintifi.ordermanagement.mapper;

import com.mintifi.ordermanagement.dto.request.CreateUserRequest;
import com.mintifi.ordermanagement.dto.response.UserResponse;
import com.mintifi.ordermanagement.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(
            CreateUserRequest request) {

        User user = new User();

        user.setUsername(
                request.getUsername());

        user.setRole(
                request.getRole());

        return user;
    }

    public static UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        return response;
    }
}
