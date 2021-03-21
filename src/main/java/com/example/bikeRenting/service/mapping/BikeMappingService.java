package com.example.bikeRenting.service.mapping;

import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.model.MainUserDetails;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class BikeMappingService {
    public BikeDTO mapToBikeDTO(Bike bike) {
        var result = new BikeDTO();
        result.setId(bike.getId());
//        result.setBikeState(bike.getBikeState().toString());
//        result.setName(bike.getName());
        result.setStationId(bike.getBikeStation().getId());
        return result;
    }
}
