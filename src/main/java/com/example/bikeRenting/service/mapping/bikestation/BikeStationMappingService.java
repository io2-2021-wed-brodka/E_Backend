package com.example.bikeRenting.service.mapping.bikestation;

import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BikeStationMappingService {
    public BikeStationDTO mapToBikeStationDTO(BikeStation bikeStation)
    {
        var result = new BikeStationDTO();
        result.setId(bikeStation.getId());
        result.setMaxBikes(bikeStation.getMaxBikes());
        result.setName(bikeStation.getLocationName());
        return result;
    }
}
