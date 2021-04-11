package com.example.bikeRenting;

import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.dto.response.RentalDTO;
import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.Rental;
import com.example.bikeRenting.model.entity.User;
import com.example.bikeRenting.service.bike.BikeMainService;
import com.example.bikeRenting.service.bikestation.BikeStationMainService;
import com.example.bikeRenting.service.mapping.bike.BikeMappingService;
import com.example.bikeRenting.service.mapping.bikestation.BikeStationMappingService;
import com.example.bikeRenting.service.mapping.rental.RentalMappingService;
import com.example.bikeRenting.service.mapping.user.UserMappingService;
import com.example.bikeRenting.service.rental.RentalMainService;
import com.example.bikeRenting.service.user.UserMainService;
import configuration.FlywayMigrationConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
@ActiveProfiles("tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MappingTests {

    @Autowired
    BikeMappingService bikeMappingService;

    @Autowired
    BikeStationMappingService bikeStationMappingService;

    @Autowired
    RentalMappingService rentalMappingService;

    @Autowired
    UserMappingService userMappingService;

    @Test
    @Order(0)
    void mapToBikeDTOTest()
    {
        Bike bike = new Bike();
        bike.setId((long) 10);
        bike.setBikeStation(null);
        bike.setRentals(null);
        var result=bikeMappingService.mapToBikeDTO(bike);
        var expected = new BikeDTO();
        expected.setId((long)10);
        expected.setStation(null);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @Order(1)
    void mapToBikeStationDTOTest()
    {
        String name = "abc";
        BikeStation bikeStation = new BikeStation();
        bikeStation.setBikes(null);
        bikeStation.setMaxBikes(300);
        bikeStation.setLocationName(name);
        bikeStation.setId((long)10);
        var result = bikeStationMappingService.mapToBikeStationDTO(bikeStation);
        var expected = new BikeStationDTO();
        expected.setId((long)10);
        expected.setMaxBikes(300);
        expected.setName(name);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @Order(2)
    void mapToRentalDTOTest()
    {
        LocalDateTime startDate = LocalDateTime.MIN;
        LocalDateTime endDate = LocalDateTime.now();
        Rental rental = new Rental();
        rental.setId((long)5);
        rental.setBike(null);
        rental.setUser(null);
        rental.setEndDate(startDate);
        rental.setStartDate(endDate);
        rental.setFromStation(null);
        rental.setToStation(null);
        Assertions.assertThrows(NullPointerException.class, ()->rentalMappingService.mapToRentalDTO(rental));
    }

    @Test
    @Order(3)
    void mapToUserDTOTest()
    {
        User user = new User();
        user.setId((long) 4);
        user.setUserName("abc");
        user.setPassword("abc");
        user.setRoles(null);
        user.setRentedBikes(null);
        var result = userMappingService.mapToUserDTO(user);
        var expected = new UserDTO();
        expected.setId((long)4);
        expected.setName("abc");
        Assertions.assertEquals(expected, result);
    }
}
