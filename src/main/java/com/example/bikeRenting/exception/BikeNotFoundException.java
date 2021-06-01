package com.example.bikeRenting.exception;

public class BikeNotFoundException extends RuntimeException {

    public BikeNotFoundException(Long bikeId) {
        super(String.format("Bike (id=%d) not found", bikeId));
    }

}
