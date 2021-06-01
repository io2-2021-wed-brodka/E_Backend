package com.example.bikeRenting.exception;

public class MalfunctionNotFoundException extends RuntimeException {

    public MalfunctionNotFoundException(String message) {
        super(message);
    }

}
