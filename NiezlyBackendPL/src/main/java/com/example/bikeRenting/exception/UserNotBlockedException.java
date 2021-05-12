package com.example.bikeRenting.exception;

import lombok.Data;

@Data
public class UserNotBlockedException extends RuntimeException {

    public UserNotBlockedException(String message) {
        super(message);
    }
}
