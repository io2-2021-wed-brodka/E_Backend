package com.example.bikeRenting.service.bikestation;

import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.repository.BikeStationRepository;
import com.example.bikeRenting.service.mapping.bikestation.BikeStationMappingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeStationMainService implements BikeStationService {

    public BikeStationMainService(BikeStationRepository bikeStationRepository, BikeStationMappingService bikeStationMappingService) {
        this.bikeStationRepository = bikeStationRepository;
        this.bikeStationMappingService = bikeStationMappingService;
    }

    private final BikeStationRepository bikeStationRepository;
    private final BikeStationMappingService bikeStationMappingService;

    @Override
    public BikeStationDTO createBikeStation(Integer maxBikes, String locationName) {
        BikeStation bikeStation = new BikeStation();
        bikeStation.setMaxBikes(maxBikes);
        bikeStation.setLocationName(locationName);
        return bikeStationMappingService.mapToBikeStationDTO(bikeStationRepository.save(bikeStation));
    }

    @Override
    public List<BikeStationDTO> findAll() {
        return bikeStationRepository.findAll().stream()
                .map(bikeStationMappingService::mapToBikeStationDTO)
                .collect(Collectors.toList());
    }
}
