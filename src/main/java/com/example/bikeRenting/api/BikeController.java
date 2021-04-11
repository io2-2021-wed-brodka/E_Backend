package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.RentalDTO;
import com.example.bikeRenting.dto.request.bike.AddBikeRequestDTO;
import com.example.bikeRenting.dto.request.bike.RentBikeRequestDTO;
import com.example.bikeRenting.service.bike.BikeService;
import com.example.bikeRenting.service.rental.RentalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

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

    @GetMapping("/rented")
    public Collection<BikeDTO> getUserRentedBikes(Principal principal) {
        return bikeService.getBikesRentedByUser(principal.getName());
    }

    @PostMapping("/rented")
    public RentalDTO rentBike(@RequestBody RentBikeRequestDTO requestDTO, Principal principal) {
        return rentalService.rentBike(requestDTO.getId(), principal.getName());
    }

    @PostMapping
    public BikeDTO addBike(@RequestBody AddBikeRequestDTO requestDTO) {
        return bikeService.addNewBike(requestDTO.getStationId());
    }
}
