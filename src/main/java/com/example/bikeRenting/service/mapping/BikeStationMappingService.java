package com.example.bikeRenting.service.mapping;

import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        var resultList = new ArrayList<BikeDTO>();
        for (var bike: bikeStation.getBikes()) {
            var result = bikeMappingService.mapToBikeDTO(bike);
        }
        return resultList;
    }
}
