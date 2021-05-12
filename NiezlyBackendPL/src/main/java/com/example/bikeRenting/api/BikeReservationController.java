package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.request.bike.ReserveBikeRequestDTO;
import com.example.bikeRenting.dto.response.ReservedBikeDTO;
import com.example.bikeRenting.service.reservation.BikeReservationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.example.bikeRenting.constants.RoleConstants.ADMIN;
import static com.example.bikeRenting.constants.RoleConstants.TECH;

@RestController
@RequestMapping("/bikes/reserved")
@Api(description = "API for operations connected with bikes reservations")
public class BikeReservationController {

    @Autowired
    private BikeReservationService bikeReservationService;

    @GetMapping
    @Secured({ADMIN, TECH})
    public List<ReservedBikeDTO> getReservedBikes() {
        return bikeReservationService.getAllReserved();
    }

    @PostMapping
    @Secured({ADMIN, TECH})
    public ReservedBikeDTO reserveBike(@RequestBody ReserveBikeRequestDTO request, Principal principal) {
        return bikeReservationService.reserveBike(request, principal.getName());
    }

    @DeleteMapping("/{reservationId}")
    public ReservedBikeDTO cancelBikeReservation(@PathVariable Long reservationId, Principal principal) {
        return bikeReservationService.cancelReservation(reservationId, principal.getName());
    }

}
