package com.example.bikeRenting.exception;

public class BikeNotFoundException extends RuntimeException {

    public BikeNotFoundException(String message) {
        super(message);
    }

}
