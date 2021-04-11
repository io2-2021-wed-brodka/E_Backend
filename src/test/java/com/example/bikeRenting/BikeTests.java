package com.example.bikeRenting;

import com.example.bikeRenting.dto.BikeDTO;
import com.example.bikeRenting.dto.BikeStationDTO;
import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.bike.BikeMainService;
import com.example.bikeRenting.service.bikestation.BikeStationMainService;
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

@SpringBootTest
@ActiveProfiles("tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BikeTests {

    @Autowired
    BikeMainService bikeMainService;
    @Autowired
    BikeStationMainService bikeStationMainService;
    @Autowired
    UserMainService userMainService;
    @Autowired
    RentalMainService rentalMainService;

    long stationOneId;
    UserDTO zeroBikesUser = new UserDTO();
    UserDTO oneBikeUser = new UserDTO();

    @BeforeAll
    void prepareData()
    {
            stationOneId = bikeStationMainService.createBikeStation(2, "Testy stacja 1").getId();
            zeroBikesUser.setName("zeroBikes");
            zeroBikesUser.setPassword("");
            zeroBikesUser.setId((long)12345);
            userMainService.createUser(zeroBikesUser);
            oneBikeUser.setName("oneBike");
            oneBikeUser.setPassword("");
            oneBikeUser.setId((long)123456);
            userMainService.createUser(oneBikeUser);
    }


    @Test
    @Order(1)
    void addBikeTest()
    {
        var result =bikeMainService.addNewBike(stationOneId);
        var expected = new BikeDTO();
        var stationDTO = new BikeStationDTO();
        stationDTO.setId(stationOneId);
        stationDTO.setName("Testy stacja 1");
        stationDTO.setMaxBikes(2);
        expected.setStation(stationDTO);
        expected.setId(result.getId()); //czy +1?
        Assertions.assertEquals(expected, result);
    }

    @Test
    @Order(2)
    void zeroBikesRentedTest()
    {
        var result = bikeMainService.getBikesRentedByUser(zeroBikesUser.getName());
        Assertions.assertNotNull(result);
        var resultTab = result.toArray();
        Assertions.assertArrayEquals(new BikeDTO[]{}, resultTab);
    }

    @Test
    @Order(3)
    void oneBikeRentedTest()
    {
        var bikeDTO=bikeMainService.addNewBike(stationOneId);
        rentalMainService.rentBike(bikeDTO.getId(), "oneBike" );
        var result = bikeMainService.getBikesRentedByUser("oneBike");
        Assertions.assertNotNull(result);
        var resultTab = result.toArray();
        bikeDTO.setStation(null);
        Assertions.assertArrayEquals(new BikeDTO[]{ bikeDTO}, resultTab);
    }
}
