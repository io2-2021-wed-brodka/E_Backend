package com.example.bikeRenting.exception;

public class StationIsFullException extends RuntimeException {

    public StationIsFullException(String message) {
        super(message);
    }

}
