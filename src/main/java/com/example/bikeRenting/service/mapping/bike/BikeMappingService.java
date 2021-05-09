package com.example.bikeRenting.service.mapping.bike;

import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.ReservedBikeDTO;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.service.mapping.bikestation.BikeStationMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BikeMappingService {

    private final BikeStationMappingService bikeStationMappingService;

    @Autowired
    public BikeMappingService(BikeStationMappingService bikeStationMappingService) {
        this.bikeStationMappingService = bikeStationMappingService;
    }

    public BikeDTO mapToBikeDTO(Bike bike) {
        var result = new BikeDTO();
        result.setId(bike.getId());
        result.setStation(bike.getBikeStation() != null ? bikeStationMappingService.mapToBikeStationDTO(bike.getBikeStation()) : null);
        result.setStatus(bike.getStatus());
        return result;
    }

    public ReservedBikeDTO mapToReservedBikeDTO(Bike bike) {
        var result = new ReservedBikeDTO();
        var reservation = Optional.ofNullable(bike.getReservation())
                .orElseThrow(() -> new RuntimeException("Bike is not reserved"));
        result.setId(bike.getId());
        result.setReservedAt(reservation.getReservedAt());
        result.setReservedTill(reservation.getReservedTill());
        result.setStation(bikeStationMappingService.mapToBikeStationDTO(bike.getBikeStation()));
        return result;
    }
}
