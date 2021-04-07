package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.dto.RentalDTO;
import com.example.bikeRenting.service.bike.BikeService;
import com.example.bikeRenting.service.bikestation.BikeStationService;
import com.example.bikeRenting.service.rental.RentalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/stations")
@Api(description = "API for operations connected with bike stations")
public class BikeStationController {
    private final BikeService bikeService;
    private final RentalService rentalService;
    private final BikeStationService bikeStationService;

    @Autowired
    public BikeStationController(BikeService bikeService, RentalService rentalService, BikeStationService bikeStationService) {
        this.bikeService = bikeService;
        this.rentalService = rentalService;
        this.bikeStationService = bikeStationService;
    }

    @GetMapping("/{stationId}/bikes")
    public Collection<BikeDTO> getBikesInStation(@PathVariable long stationId) {
        return bikeService.getBikesInStation(stationId);
    }

    @PostMapping("/{stationId}/bikes")
    public RentalDTO returnBike(@PathVariable long stationId, @RequestBody BikeDTO bikeDTO, Principal principal) {
        return rentalService.returnBike(bikeDTO.getId(), stationId, principal.getName());
    }

    @PostMapping
    public BikeStationDTO createStation(@RequestBody BikeStationDTO bikeStationDTO) {
        return bikeStationService.createBikeStation(bikeStationDTO.getMaxBikes(), bikeStationDTO.getName());
    }

    @GetMapping
    public List<BikeStationDTO> getAllBikeStations() {
        return bikeStationService.findAll();
    }
}
