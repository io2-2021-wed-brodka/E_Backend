package com.example.bikeRenting.service.bikestation;

import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;

public interface BikeStationService {

    BikeStationDTO createBikeStation(int maxBikes, String locationName);
}
