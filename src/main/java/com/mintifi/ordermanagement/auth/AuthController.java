package com.mintifi.ordermanagement.auth;

import com.mintifi.ordermanagement.entity.User;
import com.mintifi.ordermanagement.exception.InvalidCredentialsException;
import com.mintifi.ordermanagement.repository.UserRepository;
import com.mintifi.ordermanagement.util.PasswordUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    UserRepository userRepository;

    @POST
    @Path("/login")
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!PasswordUtil.verifyPassword(request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid credentials");
        }
        String token = Jwt.issuer("order-management")
                .subject(user.getUsername())
                .groups(user.getRole())
                .expiresIn(3600)
                .sign();

        return new LoginResponse(token);
    }
}

