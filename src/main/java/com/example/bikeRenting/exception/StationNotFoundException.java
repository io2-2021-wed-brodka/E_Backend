package com.example.bikeRenting.exception;

public class StationNotFoundException extends RuntimeException{

    public StationNotFoundException(String message) {
        super(message);
    }
}
