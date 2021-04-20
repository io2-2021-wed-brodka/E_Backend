package com.example.bikeRenting.service.reservation;

import com.example.bikeRenting.api.BikeReservationController;
import com.example.bikeRenting.dto.request.bike.ReserveBikeRequestDTO;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.ReservedBikeDTO;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.Reservation;
import com.example.bikeRenting.model.entity.User;
import com.example.bikeRenting.model.entity.UserStatus;
import com.example.bikeRenting.model.entity.enums.BikeStatus;
import com.example.bikeRenting.repository.BikeRepository;
import com.example.bikeRenting.repository.BikeReservationRepository;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.mapping.bike.BikeMappingService;
import com.example.bikeRenting.service.mapping.reservation.ReservationMappingService;
import com.example.bikeRenting.service.time.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeReservationService {

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private BikeReservationRepository bikeReservationRepository;

    @Autowired
    private ReservationMappingService reservationMappingService;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private BikeMappingService bikeMappingService;

    @Autowired
    private UserRepository userRepository;

    public List<ReservedBikeDTO> getAllReserved() {
        return bikeRepository.findAllByStatus(BikeStatus.RESERVED).stream()
                .map(bikeMappingService::mapToReservedBikeDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservedBikeDTO reserveBike(ReserveBikeRequestDTO request, String username) {
        // todo refactor to throwing specific exceptions
        var bike = bikeRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        if (BikeStatus.BLOCKED.equals(bike.getStatus()) || BikeStatus.RESERVED.equals(bike.getStatus())
                || BikeStatus.RENTED.equals(bike.getStatus())) {
            throw new RuntimeException("Bike already blocked, rented or reserved, or station is blocked");
        }

        var loggedUser = userRepository.findByUserName(username).orElseThrow();

        if (UserStatus.BLOCKED.equals(loggedUser.getStatus())) {
            throw new RuntimeException("User is blocked");
        }

        return reserveBike(loggedUser.getId(), bikeMappingService.mapToBikeDTO(bike));
    }

    public ReservedBikeDTO cancelReservation(Long reservationId, String username) {
        // todo
        return null;
    }

    private ReservedBikeDTO reserveBike(Long userId, BikeDTO bike) {
        var userCurrentBikeReservation = bikeReservationRepository.findByUserIdAndBikeId(userId, bike.getId());
        if (userCurrentBikeReservation.isPresent()) {
            throw new RuntimeException("Bike is already reserved by user");
        }
        var reservation = createBikeReservation(userId, bike.getId());
        return reservationMappingService.mapToReservedBike(bikeReservationRepository.save(reservation), bike.getStation());
    }

    private Reservation createBikeReservation(Long userId, Long bikeId) {
        var reservation = new Reservation();
        reservation.setUser(new User(userId));
        reservation.setBike(new Bike(bikeId));
        reservation.setReservedAt(dateTimeService.getCurrentDate());
        reservation.setReservedTill(getReservedTillDate(reservation.getReservedAt()));
        return reservation;
    }

    private LocalDateTime getReservedTillDate(LocalDateTime reservedAt) {
        return reservedAt.plusHours(1);
    }

}
