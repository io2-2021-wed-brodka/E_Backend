package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.request.bike.RentBikeRequestDTO;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.BikeListDTO;
import com.example.bikeRenting.dto.response.RentalDTO;
import com.example.bikeRenting.service.bike.BikeService;
import com.example.bikeRenting.service.rental.RentalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/bikes/rented")
@Api(description = "API for operations connected with bikes renting")
public class BikeRentedController {

    @Autowired
    private BikeService bikeService;

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public BikeListDTO getUserRentedBikes(Principal principal) {
        return new BikeListDTO(bikeService.getBikesRentedByUser(principal.getName()));
    }

    @PostMapping
    public RentalDTO rentBike(@RequestBody RentBikeRequestDTO requestDTO, Principal principal) {
        return rentalService.rentBike(requestDTO.getId(), principal.getName());
    }
}
