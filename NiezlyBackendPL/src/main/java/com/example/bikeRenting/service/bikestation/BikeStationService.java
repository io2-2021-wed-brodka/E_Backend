package com.example.bikeRenting.service.bikestation;

import com.example.bikeRenting.dto.response.BikeStationDTO;

import java.util.List;

public interface BikeStationService {

    BikeStationDTO createBikeStation(Integer maxBikes, String locationName);

    BikeStationDTO blockBikeStation(long bikeStationId);

    String unblockBikeStation(long bikeStationId);

    List<BikeStationDTO> findAll();
}
