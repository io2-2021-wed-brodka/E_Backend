package com.example.bikeRenting.service.bike;

import com.example.bikeRenting.dto.BikeDTO;

import java.util.Collection;

public interface BikeService {
    Collection<BikeDTO> getBikesInStation(long stationId);
    Collection<BikeDTO> getBikesRentedByUser(String userName);

    BikeDTO addNewBike(long stationId);
}
