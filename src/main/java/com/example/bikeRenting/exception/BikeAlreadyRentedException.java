package com.example.bikeRenting.exception;

public class BikeAlreadyRentedException extends RuntimeException {

    public BikeAlreadyRentedException(String message) {
        super(message);
    }
}
