package com.example.bikeRenting.service.mapping.bike;

import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.service.mapping.bikestation.BikeStationMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return result;
    }
}
