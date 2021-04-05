package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.service.bikestation.BikeStationService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    List<BikeStationDTO> getAllBikeStations() {
        return bikeStationService.findAll();
    }

    private final BikeStationService bikeStationService;
}
