package com.example.bikeRenting.exception;

import com.example.bikeRenting.model.entity.Bike;

public class BikeBlockedException extends RuntimeException {

    public BikeBlockedException(Bike bike) {
        super(String.format("Bike (id=%d) is blocked", bike.getId()));
    }

}
