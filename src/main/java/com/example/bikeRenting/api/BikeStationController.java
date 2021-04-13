package com.example.bikeRenting.api;

import com.example.bikeRenting.constants.RoleConstants;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.dto.response.RentalDTO;
import com.example.bikeRenting.dto.request.bikeStation.CreateStationRequestDTO;
import com.example.bikeRenting.dto.request.bikeStation.ReturnBikeRequestDTO;
import com.example.bikeRenting.service.bike.BikeService;
import com.example.bikeRenting.service.bikestation.BikeStationService;
import com.example.bikeRenting.service.rental.RentalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    public RentalDTO returnBike(@PathVariable long stationId, @RequestBody ReturnBikeRequestDTO requestDTO, Principal principal) {
        return rentalService.returnBike(requestDTO.getId(), stationId, principal.getName());
    }

    @PostMapping
    @Secured(RoleConstants.ADMIN)
    public BikeStationDTO createStation(@RequestBody CreateStationRequestDTO requestDTO) {
        return bikeStationService.createBikeStation(requestDTO.getMaxBikes(), requestDTO.getName());
    }

    @GetMapping
    public List<BikeStationDTO> getAllBikeStations() {
        return bikeStationService.findAll();
    }
}
