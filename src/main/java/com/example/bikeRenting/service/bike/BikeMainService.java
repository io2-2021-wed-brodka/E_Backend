package com.example.bikeRenting.service.bike;

import com.example.bikeRenting.dto.request.bike.ReserveBikeRequestDTO;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.ReservedBikeDTO;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.UserStatus;
import com.example.bikeRenting.model.entity.enums.BikeStatus;
import com.example.bikeRenting.repository.BikeRepository;
import com.example.bikeRenting.repository.BikeStationRepository;
import com.example.bikeRenting.repository.RentalRepository;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.mapping.bike.BikeMappingService;
import com.example.bikeRenting.service.reservation.BikeReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeMainService implements BikeService{

    private final BikeRepository bikeRepository;
    private final BikeStationRepository bikeStationRepository;
    private final BikeMappingService bikeMappingService;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    public BikeMainService(BikeRepository bikeRepository, BikeStationRepository bikeStationRepository,
                           BikeMappingService bikeMappingService, UserRepository userRepository,
                           RentalRepository rentalRepository) {
        this.bikeRepository = bikeRepository;
        this.bikeStationRepository = bikeStationRepository;
        this.bikeMappingService = bikeMappingService;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Collection<BikeDTO> getBikesInStation(long stationId) {
        return bikeStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Bike station with id " + stationId + "doesn't exist"))
                .getBikes()
                .stream().map(bikeMappingService::mapToBikeDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<BikeDTO> getBikesRentedByUser(String userName) {
        return rentalRepository.getCurrentUserRentals(userName)
                .stream().map(r -> bikeMappingService.mapToBikeDTO(r.getBike()))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public BikeDTO addNewBike(long stationId) {
        var bikeStation =  bikeStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Bike station with id " + stationId + " doesn't exist"));
        checkWhetherStationIsFull(bikeStation);
        Bike bike = new Bike();
        bike.setBikeStation(bikeStation);
        bike.setStatus(BikeStatus.ACTIVE);
        return bikeMappingService.mapToBikeDTO(bikeRepository.save(bike));
    }

    @Override
    public BikeDTO blockBike(long bikeId) {
        var bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike with given id does not exist"));
        if(BikeStatus.BLOCKED.equals(bike.getStatus())) {
            throw new RuntimeException("Bike has already been blocked");
        }

        bike.setStatus(BikeStatus.BLOCKED);

        return bikeMappingService.mapToBikeDTO(bikeRepository.save(bike));
    }

    @Override
    public BikeDTO unBlockBike(long bikeId) {
        var bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike with given id does not exist"));
        if(BikeStatus.ACTIVE.equals(bike.getStatus())) {
            throw new RuntimeException("Bike not blocked");
        }

        bike.setStatus(BikeStatus.ACTIVE);

        return bikeMappingService.mapToBikeDTO(bikeRepository.save(bike));
    }

    @Override
    public Collection<BikeDTO> getAllBlockedBikes() {
        return bikeRepository.findAllByStatus(BikeStatus.BLOCKED).stream()
                .map(bikeMappingService::mapToBikeDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public  BikeDTO deleteBike(long bikeId) {
        var bike = bikeRepository.findById(bikeId)
                .orElseThrow(()-> new RuntimeException("Bike with id " + bikeId +" does not exist"));

        if(null == bike.getBikeStation()) {
            throw new RuntimeException("Bike with id " + bikeId + " is currently rented");
        }

        var bikeDTO = bikeMappingService.mapToBikeDTO(bike);

        bikeRepository.delete(bike);

        return bikeDTO;
    }

    @Override
    public Collection<BikeDTO> findAll() {
        return bikeRepository.findAll().stream()
                .map(bikeMappingService::mapToBikeDTO)
                .collect(Collectors.toList());
    }

    private void checkWhetherStationIsFull(BikeStation bikeStation) {
        if(bikeStation.getMaxBikes() <= bikeStationRepository.getBikesCount(bikeStation.getId())) {
            throw new RuntimeException("Bike station with id " + bikeStation.getId() + " is full");
        }
    }
}
