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
        bikeStation.setStatus(BikeStation.BikeStationState.Working);
        return bikeStationMappingService.mapToBikeStationDTO(bikeStationRepository.save(bikeStation));
    }

    @Override
    public List<BikeStationDTO> findAll() {
        return bikeStationRepository.findAll().stream()
                .map(bikeStationMappingService::mapToBikeStationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BikeStationDTO blockBikeStation(long bikeStationId)
    {
        var bikeStation = bikeStationRepository.findById(bikeStationId)
                .orElseThrow(()-> new RuntimeException ("Station not found"));

        if(BikeStation.BikeStationState.Blocked == bikeStation.getStatus()) {
            throw new RuntimeException("Station already blocked");
        }

        bikeStation.setStatus(BikeStation.BikeStationState.Blocked);
        bikeStationRepository.save(bikeStation);

        return bikeStationMappingService.mapToBikeStationDTO(bikeStation);
    }

    @Override
    public String unblockBikeStation(long bikeStationId)
    {
        var bikeStation = bikeStationRepository.findById(bikeStationId)
                .orElseThrow(()-> new RuntimeException ("Station not found"));

        if(BikeStation.BikeStationState.Working == bikeStation.getStatus()) {
            throw new RuntimeException("Station not blocked");
        }

        bikeStation.setStatus(BikeStation.BikeStationState.Working);
        bikeStationRepository.save(bikeStation);

        return "Station unblocked";
    }

    @Override
    public String deleteBikeStation(Long bikeStationId) {
        var bikeStation = bikeStationRepository.findById(bikeStationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        var bikes = bikeStationRepository.getBikesCount(bikeStationId);
        if (bikes > 0) {
            throw new RuntimeException("Station is not empty");
        }

        bikeStationRepository.deleteById(bikeStationId);
        return "Station deleted";
    }
}
