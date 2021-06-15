package com.example.bikeRenting.exception;

public class BikeAlreadyDeletedException extends RuntimeException {

    public BikeAlreadyDeletedException(String message) {
        super(message);
    }
}
