package com.example.bikeRenting.exception;

import lombok.Data;

@Data
public class UserAlreadyBlockedException extends RuntimeException {

    public UserAlreadyBlockedException(String message) {
        super(message);
    }
}
