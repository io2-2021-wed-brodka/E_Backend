package com.example.bikeRenting.service.reservation;

import com.example.bikeRenting.dto.request.bike.ReserveBikeRequestDTO;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.ReservedBikeDTO;
import com.example.bikeRenting.dto.response.ReservedBikesListDTO;
import com.example.bikeRenting.model.entity.Reservation;
import com.example.bikeRenting.model.entity.User;
import com.example.bikeRenting.model.entity.UserStatus;
import com.example.bikeRenting.model.entity.enums.BikeStatus;
import com.example.bikeRenting.repository.BikeRepository;
import com.example.bikeRenting.repository.BikeReservationRepository;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.activiti.ActivitiReservationService;
import com.example.bikeRenting.service.mapping.bike.BikeMappingService;
import com.example.bikeRenting.service.mapping.reservation.ReservationMappingService;
import com.example.bikeRenting.service.time.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    @Autowired
    private ActivitiReservationService activitiReservationService;

    public ReservedBikesListDTO getReservedByUser(String username) {
        var user = userRepository.findByUserName(username).orElseThrow();
        return new ReservedBikesListDTO(bikeReservationRepository.findAllByUser(user).stream()
                .map(r -> bikeMappingService.mapToReservedBikeDTO(r.getBike()))
                .collect(Collectors.toList()));
    }

    @Transactional
    public ReservedBikeDTO reserveBike(ReserveBikeRequestDTO request, String username) {
        // todo refactor to throwing specific exceptions
        long id = -1;
        if(null!=request.getId() && !request.getId().isEmpty())
        {
            id = Long.parseLong(request.getId());
        }
        var bike = bikeRepository.findById(id)
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

    public void cancelReservation(Long bikeId, String username) {
        var bike = bikeRepository.findById(bikeId).orElseThrow(() -> new RuntimeException("Bike not found"));

        if(!bike.getStatus().equals(BikeStatus.RESERVED)) {
            throw new RuntimeException("Bike is not reserved");
        }

        if(!bike.getReservation().getUser().getUserName().equals(username)) {
            throw new RuntimeException("Bike was reserved by other user");
        }

        bike.setStatus(BikeStatus.ACTIVE);
        bike.setReservation(null);

        activitiReservationService.cancelReservationExpiration(bikeId);

        bikeRepository.save(bike);
    }

    private ReservedBikeDTO reserveBike(Long userId, BikeDTO bike) {
        long id = -1;
        if(null!=bike.getId() && !bike.getId().isEmpty())
        {
            id = Long.parseLong(bike.getId());
        }
        var userCurrentBikeReservation = bikeReservationRepository.findByUserIdAndBikeId(userId, id);
        if (userCurrentBikeReservation.isPresent()) {
            throw new RuntimeException("Bike is already reserved by user");
        }
        var reservation = createBikeReservation(userId, id);

        reservation.getBike().setStatus(BikeStatus.RESERVED);

        activitiReservationService.startReservationExpiration(id);

        return reservationMappingService.mapToReservedBike(bikeReservationRepository.save(reservation), bike.getStation());
    }

    private Reservation createBikeReservation(Long userId, Long bikeId) {
        var reservation = new Reservation();
        reservation.setUser(new User(userId));
        reservation.setBike(bikeRepository.findById(bikeId).orElseThrow());
        reservation.setReservedAt(dateTimeService.getCurrentDate());
        reservation.setReservedTill(getReservedTillDate(reservation.getReservedAt()));
        return reservation;
    }

    private LocalDateTime getReservedTillDate(LocalDateTime reservedAt) {
        return reservedAt.plusSeconds(20);
    }

}
