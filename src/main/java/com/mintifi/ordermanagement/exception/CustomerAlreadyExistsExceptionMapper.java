package com.mintifi.ordermanagement.exception;

import com.mintifi.ordermanagement.common.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerAlreadyExistsExceptionMapper
        implements ExceptionMapper<CustomerAlreadyExistsException> {

    @Override
    public Response toResponse(CustomerAlreadyExistsException exception) {

        ErrorResponse errorResponse =
                new ErrorResponse(exception.getMessage());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}