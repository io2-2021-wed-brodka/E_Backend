package com.example.bikeRenting.exception;

public class StationAlreadyBlockedException extends RuntimeException {

    public StationAlreadyBlockedException(String message) {
        super(message);
    }

}
