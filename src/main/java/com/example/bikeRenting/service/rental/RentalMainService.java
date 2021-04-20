package com.example.bikeRenting.service.rental;

import com.example.bikeRenting.dto.response.RentalDTO;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.Rental;
import com.example.bikeRenting.repository.BikeRepository;
import com.example.bikeRenting.repository.BikeStationRepository;
import com.example.bikeRenting.repository.RentalRepository;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.mapping.rental.RentalMappingService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class RentalMainService implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BikeRepository bikeRepository;
    private final RentalMappingService rentalMappingService;
    private final BikeStationRepository bikeStationRepository;

    public RentalMainService(RentalRepository rentalRepository, UserRepository userRepository, BikeRepository bikeRepository, RentalMappingService rentalMappingService, BikeStationRepository bikeStationRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bikeRepository = bikeRepository;
        this.rentalMappingService = rentalMappingService;
        this.bikeStationRepository = bikeStationRepository;
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

        if (bike.isBlocked()) {
            throw new RuntimeException("Bike is blocked");
        }

        var rental = new Rental();
        rental.setBike(bike);
        rental.setUser(user);
        rental.setStartDate(LocalDateTime.now());
        rental.setFromStation(bike.getBikeStation());

        bike.setBikeStation(null);

        bikeRepository.save(bike);

        return rentalMappingService.mapToRentalDTO(rentalRepository.save(rental));
    }

    @Override
    @Transactional
    public RentalDTO returnBike(long bikeId, long bikeStationId, String userName) {
        var rental = rentalRepository.getCurrentBikeRental(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike is not rented"));

        var user = rental.getUser();

        if(!user.getUserName().equals(userName)) {
            throw new RuntimeException("Bike was rented by other user");
        }

        var bikeStation = bikeStationRepository.findById(bikeStationId)
                .orElseThrow(() -> new RuntimeException("bike station with id " + bikeStationId + " doesn't exist"));

        checkWhetherStationIsFull(bikeStation);

        rental.getBike().setBikeStation(bikeStation);
        rental.setEndDate(LocalDateTime.now());
        rental.setToStation(bikeStation);

        return rentalMappingService.mapToRentalDTO(rentalRepository.save(rental));
    }

    private void checkWhetherStationIsFull(BikeStation bikeStation) {
        if(bikeStation.getMaxBikes() <= bikeStationRepository.getBikesCount(bikeStation.getId())) {
            throw new RuntimeException("Bike station with id " + bikeStation.getId() + " is full");
        }
    }
}
