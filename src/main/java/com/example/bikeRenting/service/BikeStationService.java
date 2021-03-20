package com.example.bikeRenting.service;

import com.example.bikeRenting.dto.BikeStationDTO;

public interface BikeStationService {

    BikeStationDTO createBikeStation(int maxBikes, String locationName);


}
