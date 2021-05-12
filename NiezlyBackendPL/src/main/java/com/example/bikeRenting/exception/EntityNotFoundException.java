package com.example.bikeRenting.exception;

import lombok.Data;

@Data
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
