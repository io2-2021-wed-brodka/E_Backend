package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.dto.RentalDTO;
import com.example.bikeRenting.service.BikeService;
import com.example.bikeRenting.service.BikeStationService;
import com.example.bikeRenting.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/stations")
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
}
