package com.example.bikeRenting.service.mapping.rental;

import com.example.bikeRenting.dto.response.RentalDTO;
import com.example.bikeRenting.model.entity.Rental;
import com.example.bikeRenting.service.mapping.bike.BikeMappingService;
import com.example.bikeRenting.service.mapping.user.UserMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RentalMappingService {

    private final UserMappingService userMappingService;

    public RentalMappingService(UserMappingService userMappingService) {
        this.userMappingService = userMappingService;
    }

    public RentalDTO mapToRentalDTO(Rental rental) {
        var result = new RentalDTO();
        result.setId(rental.getBike().getId().toString());
        result.setUser(userMappingService.mapToUserDTO(rental.getUser()));
        return result;
    }
}
