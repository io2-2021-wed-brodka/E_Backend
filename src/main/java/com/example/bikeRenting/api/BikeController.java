package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.request.bike.AddBikeRequestDTO;
import com.example.bikeRenting.dto.request.bike.BlockBikeRequestDTO;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.BikeListDTO;
import com.example.bikeRenting.service.bike.BikeService;
import com.example.bikeRenting.service.rental.RentalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Secured(ADMIN)
    public BikeDTO addBike(@RequestBody AddBikeRequestDTO requestDTO) {
        long id = -1;
        if(null!=requestDTO.getStationId() && !requestDTO.getStationId().isEmpty())
        {
            id = Long.parseLong(requestDTO.getStationId());
        }
        return bikeService.addNewBike(id);
    }

    @Secured({ADMIN, TECH})
    @GetMapping("/blocked")
    public BikeListDTO getAllBlockedBikes() {
        return new BikeListDTO(bikeService.getAllBlockedBikes());
    }

    @Secured({ADMIN, TECH})
    @PostMapping("/blocked")
    public BikeDTO blockBike(@RequestBody BlockBikeRequestDTO requestDTO) {
        long id = -1;
        if(null!=requestDTO.getId() && !requestDTO.getId().isEmpty())
        {
            id = Long.parseLong(requestDTO.getId());
        }
        return bikeService.blockBike(id);
    }

    @Secured({ADMIN, TECH})
    @DeleteMapping("/blocked/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unBlockBike(@PathVariable("id") long id) {
        bikeService.unBlockBike(id);
    }

    @DeleteMapping("/{id}")
    @Secured(ADMIN)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBike(@PathVariable long id) {
        bikeService.deleteBike(id);
    }

    @GetMapping
    @Secured({TECH, ADMIN})
    public BikeListDTO getAllBikes() {
        return new BikeListDTO(bikeService.findAll());
    }
}
