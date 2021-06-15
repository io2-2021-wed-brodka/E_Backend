package com.example.bikeRenting.exception;

public class BikeAlreadyBlockedException extends RuntimeException {

    public BikeAlreadyBlockedException(String message) {
        super(message);
    }
}
