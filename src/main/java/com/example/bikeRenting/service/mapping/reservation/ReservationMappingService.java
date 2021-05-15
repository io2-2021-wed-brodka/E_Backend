package com.example.bikeRenting.service.mapping.reservation;

import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.dto.response.ReservedBikeDTO;
import com.example.bikeRenting.model.entity.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationMappingService {

    public ReservedBikeDTO mapToReservedBike(Reservation reservation, BikeStationDTO bikeStation) {
        var reservedBike = new ReservedBikeDTO();
        reservedBike.setReservedAt(reservedBike.getReservedAt());
        reservedBike.setReservedTill(reservedBike.getReservedTill());
        reservedBike.setStation(bikeStation);
        reservedBike.setId(reservation.getBike().getId());
        return reservedBike;
    }
}
