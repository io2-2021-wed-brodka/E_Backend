package com.example.bikeRenting.service.mapping;

import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.BikeStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BikeMappingService {
    public BikeDTO mapToBikeDTO(Bike bike) {
        var result = new BikeDTO();
        result.setId(bike.getId());
//        result.setBikeState(bike.getBikeState().toString());
        result.setStationId(Optional.ofNullable(bike.getBikeStation()).map(BikeStation::getId).orElse(null));
        return result;
    }
}
