package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.service.bikestation.BikeStationService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stations")
@Api(description = "API for operations connected with bike stations")
public class BikeStationController {
    public BikeStationController(BikeStationService bikeStationService) {
        this.bikeStationService = bikeStationService;
    }

    @PostMapping
    BikeStationDTO createStation(@RequestBody BikeStationDTO bikeStationDTO) {
        return bikeStationService.createBikeStation(bikeStationDTO.getMaxBikes(), bikeStationDTO.getLocationName());
    }

    private final BikeStationService bikeStationService;
}
