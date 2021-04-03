package com.example.bikeRenting.service.mapping;

import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
