package com.example.bikeRenting.api;

import ch.qos.logback.core.subst.Parser;
import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.RentalDTO;
import com.example.bikeRenting.service.BikeService;
import com.example.bikeRenting.service.RentalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/bikes")
@Api(description = "API for operations connected with bikes")
public class BikeController {

    private final BikeService bikeService;
    private final RentalService rentalService;

    @Autowired
    public BikeController(BikeService bikeService, RentalService rentalService) {
        this.bikeService = bikeService;
        this.rentalService = rentalService;
    }

    @GetMapping("/{stationId}")
    public Collection<BikeDTO> getBikesInStation(@PathVariable long stationId) {
        return bikeService.getBikesInStation(stationId);
    }

    @GetMapping("/rented")
    public Collection<BikeDTO> getUserRentedBikes(Principal principal) {
        return bikeService.getBikesRentedByUser(principal.getName());
    }

    @PostMapping("/rented")
    public RentalDTO rentBike(@RequestBody BikeDTO bikeDTO, Principal principal) {
        return rentalService.rentBike(bikeDTO.getId(), principal.getName());
    }

    @DeleteMapping("/rented")
    public RentalDTO returnBike(@RequestParam Map<String,String> requestParams, Principal principal) {
        long bikeId =  Long.parseLong(requestParams.get("bikeId"));
        long stationId = Long.parseLong(requestParams.get("stationId"));
        return  rentalService.returnBike(bikeId, stationId, principal.getName());
    }

    @PostMapping("/add")
    public BikeDTO addBike(@RequestBody BikeDTO bikeDTO) {
        return bikeService.addNewBike(bikeDTO.getStationId());
    }
}
