package com.example.bikeRenting.api;

import ch.qos.logback.core.subst.Parser;
import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.RentalDTO;
import com.example.bikeRenting.service.BikeService;
import com.example.bikeRenting.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    private final BikeService bikeService;
    private final RentalService rentalService;

    @Autowired
    public BikeController(BikeService bikeService, RentalService rentalService) {
        this.bikeService = bikeService;
        this.rentalService = rentalService;
    }

    @GetMapping("/rented")
    public Collection<BikeDTO> getUserRentedBikes(Principal principal) {
        return bikeService.getBikesRentedByUser(principal.getName());
    }

    @PostMapping("/rented")
    public RentalDTO rentBike(@RequestBody BikeDTO bikeDTO, Principal principal) {
        return rentalService.rentBike(bikeDTO.getId(), principal.getName());
    }

    @PostMapping
    public BikeDTO addBike(@RequestBody BikeDTO bikeDTO) {
        return bikeService.addNewBike(bikeDTO.getStationId());
    }
}
