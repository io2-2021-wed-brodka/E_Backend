package com.example.bikeRenting.service;

import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.repository.BikeStationRepository;
import com.example.bikeRenting.service.mapping.BikeStationMappingService;
import org.springframework.stereotype.Service;

@Service
public class BikeStationMainService implements BikeStationService {

    public BikeStationMainService(BikeStationRepository bikeStationRepository, BikeStationMappingService bikeStationMappingService) {
        this.bikeStationRepository = bikeStationRepository;
        this.bikeStationMappingService = bikeStationMappingService;
    }

    private final BikeStationRepository bikeStationRepository;
    private final BikeStationMappingService bikeStationMappingService;

    @Override
    public BikeStationDTO createBikeStation(int maxBikes, String locationName) {
        BikeStation bikeStation = new BikeStation();
        bikeStation.setMaxBikes(maxBikes);
        bikeStation.setLocationName(locationName);
        return bikeStationMappingService.mapToBikeStationDTO(bikeStationRepository.save(bikeStation));
    }
}
