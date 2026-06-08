package com.mintifi.ordermanagement.controller;

import com.mintifi.ordermanagement.dto.request.CreateOrderRequest;
import com.mintifi.ordermanagement.dto.request.UpdateOrderStatusRequest;
import com.mintifi.ordermanagement.dto.response.OrderResponse;
import com.mintifi.ordermanagement.service.OrderService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DefaultValue;
//import java.util.List;

@Path("/api/v1/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

    @Inject
    OrderService orderService;

    @POST
    @Operation(
            summary = "Create Order",
            description = "Creates a new order for an existing customer"
    )
    public Response createOrder(@Valid CreateOrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") Long id) {
        return Response.ok(orderService.getOrderById(id)).build();
    }
    @GET
    public Response getAllOrders(
            @QueryParam("page")
            @DefaultValue("0")
            int page,

            @QueryParam("size")
            @DefaultValue("10")
            int size) {

        return Response.ok(orderService.getAllOrders(page, size)).build();
    }
    @PATCH
    @Path("/{id}/status")
    public Response updateOrderStatus(
            @PathParam("id") Long id,
            @Valid UpdateOrderStatusRequest request) {

        return Response.ok(orderService.updateOrderStatus(id, request)).build();
    }
}