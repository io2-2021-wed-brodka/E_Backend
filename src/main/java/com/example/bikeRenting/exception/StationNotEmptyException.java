package com.example.bikeRenting.exception;

import lombok.Data;

@Data
public class StationNotEmptyException extends RuntimeException {

    public StationNotEmptyException(String message) {
        super(message);
    }
}
