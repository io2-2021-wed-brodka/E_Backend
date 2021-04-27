package com.example.bikeRenting.exception;

import lombok.Data;

@Data
public class UserBlockedException extends RuntimeException {

    public UserBlockedException(String message) {
        super(message);
    }
}
