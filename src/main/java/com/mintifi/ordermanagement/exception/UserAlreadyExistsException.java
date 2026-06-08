package com.mintifi.ordermanagement.exception;

public class UserAlreadyExistsException
        extends RuntimeException {

    public UserAlreadyExistsException(
            String message) {

        super(message);
    }
}