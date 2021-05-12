package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.request.bike.BlockBikeRequestDTO;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.request.bike.AddBikeRequestDTO;
import com.example.bikeRenting.service.bike.BikeService;
import com.example.bikeRenting.service.rental.RentalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.example.bikeRenting.constants.RoleConstants.ADMIN;
import static com.example.bikeRenting.constants.RoleConstants.TECH;

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

    @PostMapping
    public BikeDTO addBike(@RequestBody AddBikeRequestDTO requestDTO) {
        return bikeService.addNewBike(requestDTO.getStationId());
    }

    @Secured({ADMIN, TECH})
    @GetMapping("/blocked")
    public Collection<BikeDTO> getAllBlockedBikes() {
        return bikeService.getAllBlockedBikes();
    }

    @Secured({ADMIN, TECH})
    @PostMapping("/blocked")
    public BikeDTO blockBike(@RequestBody BlockBikeRequestDTO requestDTO) {
        return bikeService.blockBike(requestDTO.getId());
    }

    @Secured({ADMIN, TECH})
    @DeleteMapping("/blocked/{id}")
    public BikeDTO unBlockBike(@PathVariable("id") long id) {
        return bikeService.unBlockBike(id);
    }

    @DeleteMapping("/{id}")
    @Secured(ADMIN)
    public BikeDTO deleteBike(@PathVariable long id) {
        return bikeService.deleteBike(id);
    }

}
