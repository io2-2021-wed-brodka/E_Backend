package com.example.bikeRenting.service.mapping;

import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BikeStationMappingService {

    @Autowired
    public BikeStationMappingService(BikeMappingService bikeMappingService) {
        this.bikeMappingService = bikeMappingService;
    }

    private final BikeMappingService bikeMappingService;

    public BikeStationDTO mapToBikeStationDTO(BikeStation bikeStation)
    {
        var result = new BikeStationDTO();
        result.setBikes(mapBikesFromStation(bikeStation));
        result.setId(bikeStation.getId());
        result.setMaxBikes(bikeStation.getMaxBikes());
        result.setLocationName(bikeStation.getLocationName());
        return result;
    }

    private List<BikeDTO> mapBikesFromStation(BikeStation bikeStation)
    {
        if(null==bikeStation.getBikes())
        {
            return new LinkedList<BikeDTO>();
        }
        return bikeStation.getBikes().stream().map(b -> bikeMappingService.mapToBikeDTO(b)).collect(Collectors.toList());
    }
}
