package com.example.bikeRenting.service.rental;

import com.example.bikeRenting.dto.response.RentalDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.Rental;
import com.example.bikeRenting.model.entity.enums.BikeStatus;
import com.example.bikeRenting.repository.BikeRepository;
import com.example.bikeRenting.repository.BikeStationRepository;
import com.example.bikeRenting.repository.RentalRepository;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.activiti.ActivitiReservationService;
import com.example.bikeRenting.service.mapping.rental.RentalMappingService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.example.bikeRenting.model.entity.BikeStation.BikeStationState.Blocked;

@Service
public class RentalMainService implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BikeRepository bikeRepository;
    private final RentalMappingService rentalMappingService;
    private final BikeStationRepository bikeStationRepository;
    private final ActivitiReservationService activitiReservationService;

    public RentalMainService(RentalRepository rentalRepository, UserRepository userRepository,
                             BikeRepository bikeRepository, RentalMappingService rentalMappingService,
                             BikeStationRepository bikeStationRepository, ActivitiReservationService activitiReservationService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bikeRepository = bikeRepository;
        this.rentalMappingService = rentalMappingService;
        this.bikeStationRepository = bikeStationRepository;
        this.activitiReservationService = activitiReservationService;
    }

    @Override
    @Transactional
    public RentalDTO rentBike(long bikeId, String userName) {
        var user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + userName + " doesn't exist"));

        var bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike with id " + bikeId + " doesn't exist"));


        if (bike.getBikeStation() == null) {
            throw new RuntimeException("Bike has been already rented");
        }

        if (BikeStatus.BLOCKED.equals(bike.getStatus())) {
            throw new RuntimeException("Bike is blocked");
        }
        if (BikeStatus.RESERVED.equals(bike.getStatus())) {
            if (bike.getReservation().getUser().getUserName().equals(userName)) {
                activitiReservationService.cancelReservationExpiration(bikeId);
            } else {
                throw new RuntimeException("Bike is reserved");
            }
        }

        checkWhetherStationIsBlocked(bike.getBikeStation());

        var rental = new Rental();
        rental.setBike(bike);
        rental.setUser(user);
        rental.setStartDate(LocalDateTime.now());
        rental.setFromStation(bike.getBikeStation());

        bike.setBikeStation(null);
        bike.setStatus(BikeStatus.RENTED);

        bikeRepository.save(bike);

        return rentalMappingService.mapToRentalDTO(rentalRepository.save(rental));
    }

    @Override
    @Transactional
    public RentalDTO returnBike(long bikeId, long bikeStationId, String userName) {
        var rental = rentalRepository.getCurrentBikeRental(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike is not rented"));

        var user = rental.getUser();

        if (!user.getUserName().equals(userName)) {
            throw new RuntimeException("Bike was rented by other user");
        }

        var bikeStation = bikeStationRepository.findById(bikeStationId)
                .orElseThrow(() -> new RuntimeException("bike station with id " + bikeStationId + " doesn't exist"));

        checkWhetherStationIsFull(bikeStation);
        checkWhetherStationIsBlocked(bikeStation);

        rental.getBike().setBikeStation(bikeStation);
        rental.getBike().setStatus(BikeStatus.ACTIVE);
        rental.setEndDate(LocalDateTime.now());
        rental.setToStation(bikeStation);

        return rentalMappingService.mapToRentalDTO(rentalRepository.save(rental));
    }

    private void checkWhetherStationIsFull(BikeStation bikeStation) {
        if (bikeStation.getMaxBikes() <= bikeStationRepository.getBikesCount(bikeStation.getId())) {
            throw new RuntimeException("Bike station with id " + bikeStation.getId() + " is full");
        }
    }

    private void checkWhetherStationIsBlocked(BikeStation bikeStation) {
        if (bikeStation.getStatus().equals(Blocked)) {
            throw new RuntimeException("Bike station with id " + bikeStation.getId() + " is blocked");
        }
    }
}
