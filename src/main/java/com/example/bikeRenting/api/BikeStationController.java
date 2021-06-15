package com.example.bikeRenting.api;

import com.example.bikeRenting.constants.RoleConstants;
import com.example.bikeRenting.dto.request.bikeStation.BlockStationRequestDTO;
import com.example.bikeRenting.dto.response.*;
import com.example.bikeRenting.dto.request.bikeStation.CreateStationRequestDTO;
import com.example.bikeRenting.dto.request.bikeStation.ReturnBikeRequestDTO;
import com.example.bikeRenting.model.entity.BikeStation;
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
import java.util.stream.Collectors;

import static com.example.bikeRenting.constants.RoleConstants.ADMIN;
import static com.example.bikeRenting.constants.RoleConstants.TECH;

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

    @GetMapping("/{stationId}/bikes/old")
    public BikeListDTO getBikesInStation(@PathVariable long stationId) {
        var result = new BikeListDTO();
        result.setBikes(bikeService.getBikesInStation(stationId));
        return result;
    }

    @GetMapping("/{stationId}")
    public BikeStationDTO getBikeStationById(@PathVariable long stationId) {
        return bikeStationService.getStationById(stationId);
    }


    @GetMapping("/{stationId}/bikes")
    public BikeListDTO returnBike(@PathVariable long stationId) {
        return bikeService.getStationActiveBikes(stationId);
    }

    @PostMapping("/{stationId}/bikes")
    public RentalDTO returnBike(@PathVariable long stationId, @RequestBody ReturnBikeRequestDTO requestDTO, Principal principal) {
        long id = -1;
        if(null!=requestDTO.getId() && !requestDTO.getId().isEmpty())
        {
            id = Long.parseLong(requestDTO.getId());
        }
        return rentalService.returnBike(id, stationId, principal.getName());
    }

    @PostMapping
    @Secured(RoleConstants.ADMIN)
    public BikeStationDTO createStation(@RequestBody CreateStationRequestDTO requestDTO) {
        return bikeStationService.createBikeStation(requestDTO.getBikesLimit(), requestDTO.getName());
    }

    @GetMapping
    public BikeStationListDTO getAllBikeStations() {
        return new BikeStationListDTO(bikeStationService.findAll());
    }

    @GetMapping("/active")
    public BikeStationListDTO getAllActiveBikeStations() {
        return new BikeStationListDTO(bikeStationService.findAll().stream().filter((x)->x.getStatus()== BikeStationDTO.StationState.active).collect(Collectors.toList()));
    }

    @PostMapping("/blocked")
    @Secured({ADMIN})
    public BikeStationDTO blockStation(@RequestBody BlockStationRequestDTO request) {
        return bikeStationService.blockBikeStation(request);
    }

    @DeleteMapping("/blocked/{id}")
    @Secured({ADMIN})
    public MessageResponseDTO unblockStation(@PathVariable long id) {
        return bikeStationService.unblockBikeStation(id);
    }

    @GetMapping("/blocked")
    public BikeStationListDTO getBlockedStations() {
        return bikeStationService.getBlockedStations();
    }

    @DeleteMapping("/{stationId}")
    @Secured({ADMIN})
    public MessageResponseDTO deleteStation(@PathVariable Long stationId) {
        return bikeStationService.deleteBikeStation(stationId);
    }
}
