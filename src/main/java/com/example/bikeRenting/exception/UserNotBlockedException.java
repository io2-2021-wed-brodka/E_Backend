package com.example.bikeRenting.exception;

import com.example.bikeRenting.model.entity.User;
import lombok.Data;

@Data
public class UserNotBlockedException extends RuntimeException {

    public UserNotBlockedException(String message) {
        super(message);
    }
}
