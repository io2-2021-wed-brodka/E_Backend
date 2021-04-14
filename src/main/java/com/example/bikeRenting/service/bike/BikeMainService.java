package com.example.bikeRenting.service.bike;

import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.repository.BikeRepository;
import com.example.bikeRenting.repository.BikeStationRepository;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.mapping.bike.BikeMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BikeMainService implements BikeService{

    private final BikeRepository bikeRepository;
    private final BikeStationRepository bikeStationRepository;
    private final BikeMappingService bikeMappingService;
    private final UserRepository userRepository;

    @Autowired
    public BikeMainService(BikeRepository bikeRepository, BikeStationRepository bikeStationRepository, BikeMappingService bikeMappingService, UserRepository userRepository) {
        this.bikeRepository = bikeRepository;
        this.bikeStationRepository = bikeStationRepository;
        this.bikeMappingService = bikeMappingService;
        this.userRepository = userRepository;
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
    @Transactional
    public Collection<BikeDTO> getBikesRentedByUser(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + userName + " doesn't exist"))
                .getRentedBikes()
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
        return bikeMappingService.mapToBikeDTO(bikeRepository.save(bike));
    }

    @Override
    public BikeDTO blockBike(long bikeId) {
        var bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike with given id does not exist"));
        if(bike.isBlocked()) {
            throw new RuntimeException("Bike has already been blocked");
        }

        bike.setBlocked(true);

        return bikeMappingService.mapToBikeDTO(bikeRepository.save(bike));
    }

    @Override
    public BikeDTO unBlockBike(long bikeId) {
        var bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike with given id does not exist"));
        if(!bike.isBlocked()) {
            throw new RuntimeException("Bike has already been blocked");
        }

        bike.setBlocked(false);

        return bikeMappingService.mapToBikeDTO(bikeRepository.save(bike));
    }

    @Override
    public Collection<BikeDTO> getAllBlockedBikes() {
        return bikeRepository.getAllByIsBlocked(true).stream()
                .map(bikeMappingService::mapToBikeDTO)
                .collect(Collectors.toList());
    }

    private void checkWhetherStationIsFull(BikeStation bikeStation) {
        if(bikeStation.getMaxBikes() <= bikeStationRepository.getBikesCount(bikeStation.getId())) {
            throw new RuntimeException("Bike station with id " + bikeStation.getId() + " is full");
        }
    }
}
