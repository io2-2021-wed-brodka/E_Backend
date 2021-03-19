package com.example.bikeRenting.service;

import com.example.bikeRenting.dto.RentalDTO;

public interface RentalService {
    RentalDTO rentBike(long bikeId, String userName);

    RentalDTO returnBike(long bikeId, long bikeStationId,String userName);
}
