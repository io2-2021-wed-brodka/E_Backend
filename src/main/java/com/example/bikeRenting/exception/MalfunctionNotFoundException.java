package com.example.bikeRenting.exception;

public class MalfunctionNotFoundException extends RuntimeException {

    public MalfunctionNotFoundException(Long malfunctionId) {
        super(String.format("Malfunction (id=%d) not found", malfunctionId));
    }

}
