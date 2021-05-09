package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.request.bike.ReserveBikeRequestDTO;
import com.example.bikeRenting.dto.response.ReservedBikeDTO;
import com.example.bikeRenting.service.reservation.BikeReservationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/bikes/reserved")
@Api(description = "API for operations connected with bikes reservations")
public class BikeReservationController {

    @Autowired
    private BikeReservationService bikeReservationService;

    @GetMapping
    public List<ReservedBikeDTO> getReservedBikes(Principal principal) {
        return bikeReservationService.getReservedByUser(principal.getName());
    }

    @PostMapping
    public ReservedBikeDTO reserveBike(@RequestBody ReserveBikeRequestDTO request, Principal principal) {
        return bikeReservationService.reserveBike(request, principal.getName());
    }

    @DeleteMapping("/{bikeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBikeReservation(@PathVariable Long bikeId, Principal principal) {
        bikeReservationService.cancelReservation(bikeId, principal.getName());
    }

}
