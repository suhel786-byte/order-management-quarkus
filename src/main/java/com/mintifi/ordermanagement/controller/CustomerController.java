package com.mintifi.ordermanagement.controller;

import com.mintifi.ordermanagement.dto.request.CreateCustomerRequest;
import com.mintifi.ordermanagement.dto.response.CustomerResponse;
import com.mintifi.ordermanagement.service.CustomerService;
import com.mintifi.ordermanagement.dto.request.UpdateCustomerRequest;
import com.mintifi.ordermanagement.service.OrderService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import java.util.List;

@Path("/api/v1/customers")
@RolesAllowed("ADMIN")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CustomerController {
    @Inject
    CustomerService customerService;


    @POST
    @Operation(summary = "Create Customer", description = "Creates a new customer"
    )
    public Response createCustomer(@Valid CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Long id) {

        CustomerResponse response = customerService.getCustomerById(id);
        return Response.ok(response).build();
    }
    @GET
    public Response getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return Response.ok(customers).build();
    }
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") Long id, @Valid UpdateCustomerRequest request) {

        CustomerResponse response = customerService.updateCustomer(id, request);
        return Response.ok(response).build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        customerService.deleteCustomer(id);
        return Response.noContent().build();
    }
    @Inject
    OrderService orderService;
    @GET
    @Path("/{customerId}/orders")
    public Response getOrdersByCustomerId(
            @PathParam("customerId") Long customerId) {

        return Response.ok(orderService.getOrdersByCustomerId(customerId)).build();
    }
}