package com.mintifi.ordermanagement.service;

import com.mintifi.ordermanagement.dto.request.CreateUserRequest;
import com.mintifi.ordermanagement.dto.response.UserResponse;
import com.mintifi.ordermanagement.entity.User;
import com.mintifi.ordermanagement.exception.UserAlreadyExistsException;
import com.mintifi.ordermanagement.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserSuccessfully() {

        CreateUserRequest request =
                new CreateUserRequest();

        request.setUsername("john");
        request.setPassword("john123");
        request.setRole("USER");

        when(userRepository.findByUsername("john"))
                .thenReturn(null);

        UserResponse response =
                userService.createUser(request);

        assertNotNull(response);
        assertEquals("john",
                response.getUsername());

        verify(userRepository,
                times(1))
                .persist(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {

        CreateUserRequest request =
                new CreateUserRequest();

        request.setUsername("admin");
        request.setPassword("admin123");
        request.setRole("ADMIN");

        User existingUser = new User();
        existingUser.setUsername("admin");

        when(userRepository.findByUsername("admin"))
                .thenReturn(existingUser);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.createUser(request)
        );
    }
}