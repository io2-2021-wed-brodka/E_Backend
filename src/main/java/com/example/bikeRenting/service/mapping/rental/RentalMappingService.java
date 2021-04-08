package com.example.bikeRenting.service.mapping.rental;

import com.example.bikeRenting.dto.RentalDTO;
import com.example.bikeRenting.model.entity.Rental;
import com.example.bikeRenting.service.mapping.bike.BikeMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RentalMappingService {

    private final BikeMappingService bikeMappingService;

    public RentalMappingService(BikeMappingService bikeMappingService) {
        this.bikeMappingService = bikeMappingService;
    }

    public RentalDTO mapToRentalDTO(Rental rental) {
        var result = new RentalDTO();
        result.setId(rental.getId());
        result.setBike(bikeMappingService.mapToBikeDTO(rental.getBike()));
        result.setStartDate(rental.getStartDate());
        result.setEndDate(rental.getEndDate());
        return result;
    }
}