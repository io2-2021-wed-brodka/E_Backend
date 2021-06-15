package com.example.bikeRenting.service.bikestation;

import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.dto.response.BikeStationListDTO;
import com.example.bikeRenting.dto.response.MessageResponseDTO;
import com.example.bikeRenting.exception.StationNotEmptyException;
import com.example.bikeRenting.exception.StationNotFoundException;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.enums.BikeStatus;
import com.example.bikeRenting.repository.BikeStationRepository;
import com.example.bikeRenting.service.mapping.bikestation.BikeStationMappingService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        bikeStation.setMaxBikes(maxBikes==null?10:maxBikes);
        bikeStation.setLocationName(locationName);
        bikeStation.setStatus(BikeStation.BikeStationState.Working);
        return bikeStationMappingService.mapToBikeStationDTO(bikeStationRepository.save(bikeStation));
    }

    @Override
    public List<BikeStationDTO> findAll() {
        return bikeStationRepository.findAllNotStatus(BikeStation.BikeStationState.Deleted).stream()
                .map(bikeStationMappingService::mapToBikeStationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BikeStationDTO blockBikeStation(long bikeStationId) {
        var bikeStation = bikeStationRepository.findById(bikeStationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        if(checkIfDeleted(bikeStation)) {
            throw new StationNotFoundException("Station not found");
        }

        if (BikeStation.BikeStationState.Blocked == bikeStation.getStatus()) {
            throw new RuntimeException("Station already blocked");
        }

        bikeStation.setStatus(BikeStation.BikeStationState.Blocked);
        bikeStationRepository.save(bikeStation);

        return bikeStationMappingService.mapToBikeStationDTO(bikeStation);
    }

    @Override
    public MessageResponseDTO unblockBikeStation(long bikeStationId) {
        var bikeStation = bikeStationRepository.findById(bikeStationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        if(checkIfDeleted(bikeStation)) {
            throw new StationNotFoundException("Station not found");
        }

        if (BikeStation.BikeStationState.Working == bikeStation.getStatus()) {
            throw new RuntimeException("Station not blocked");
        }

        bikeStation.setStatus(BikeStation.BikeStationState.Working);
        bikeStationRepository.save(bikeStation);

        return new MessageResponseDTO("Station unblocked");
    }

    @Override
    @Transactional
    public MessageResponseDTO deleteBikeStation(Long bikeStationId) {

        var station = bikeStationRepository.findById(bikeStationId)
                .orElseThrow(() -> new StationNotFoundException("Station does not exist"));

        if(checkIfDeleted(station)) {
            throw new StationNotFoundException("Station does not exist");
        }

        var bikes = bikeStationRepository.getBikesCount(bikeStationId);
        if (bikes > 0) {
            throw new StationNotEmptyException("Station is not empty");
        }

        station.setStatus(BikeStation.BikeStationState.Deleted);
        bikeStationRepository.save(station);
        return new MessageResponseDTO("Station deleted");
    }

    @Override
    public BikeStationListDTO getBlockedStations() {
        var blockedStations = bikeStationRepository.findAllNotStatus(BikeStation.BikeStationState.Blocked).stream()
                .map(bikeStationMappingService::mapToBikeStationDTO)
                .collect(Collectors.toList());
        return new BikeStationListDTO(blockedStations);
    }

    private boolean checkIfDeleted(BikeStation bikeStation) {
        return BikeStation.BikeStationState.Deleted == bikeStation.getStatus();
    }
}
