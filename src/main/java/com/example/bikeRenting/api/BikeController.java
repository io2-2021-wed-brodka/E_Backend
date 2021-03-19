package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.RentalDTO;
import com.example.bikeRenting.service.BikeService;
import com.example.bikeRenting.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

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
}
