package com.example.bikeRenting.service.bikestation;

import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.dto.response.MessageResponseDTO;

import java.util.List;

public interface BikeStationService {

    BikeStationDTO createBikeStation(Integer maxBikes, String locationName);

    BikeStationDTO blockBikeStation(long bikeStationId);

    MessageResponseDTO unblockBikeStation(long bikeStationId);

    List<BikeStationDTO> findAll();

    MessageResponseDTO deleteBikeStation(Long bikeStationId);
}
