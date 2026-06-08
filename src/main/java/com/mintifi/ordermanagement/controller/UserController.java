package com.mintifi.ordermanagement.controller;

import com.mintifi.ordermanagement.dto.request.CreateUserRequest;
import com.mintifi.ordermanagement.dto.response.UserResponse;
import com.mintifi.ordermanagement.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PathParam;
import java.util.List;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class UserController {

    @Inject
    UserService userService;

    @POST
    public Response createUser(
            @Valid CreateUserRequest request) {

        UserResponse response = userService.createUser(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
    @GET
    public Response getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();

        return Response.ok(users).build();
    }
    @GET
    @Path("/{id}")
    public Response getUserById(
            @PathParam("id") Long id) {

        UserResponse response = userService.getUserById(id);
        return Response.ok(response).build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteUser(
            @PathParam("id") Long id) {

        userService.deleteUser(id);
        return Response.noContent().build();
    }
}
