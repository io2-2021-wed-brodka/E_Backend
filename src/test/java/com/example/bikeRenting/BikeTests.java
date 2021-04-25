package com.example.bikeRenting;


import com.example.bikeRenting.dto.request.login.LoginRequestDTO;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.service.bike.BikeMainService;
import com.example.bikeRenting.service.bikestation.BikeStationMainService;
import com.example.bikeRenting.service.rental.RentalMainService;
import com.example.bikeRenting.service.user.UserMainService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;


@SpringBootTest
@ActiveProfiles("unit-tests")
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
    LoginRequestDTO zeroBikesUser = new LoginRequestDTO();
    LoginRequestDTO oneBikeUser = new LoginRequestDTO();

    @BeforeAll
    void prepareData()
    {
            stationOneId = bikeStationMainService.createBikeStation(2, "Testy stacja 1").getId();
            zeroBikesUser.setLogin("zeroBikes");
            zeroBikesUser.setPassword("");
            userMainService.createUser(zeroBikesUser.getLogin(), zeroBikesUser.getPassword());
            oneBikeUser.setLogin("oneBike");
            oneBikeUser.setPassword("");
            userMainService.createUser(oneBikeUser.getLogin(), oneBikeUser.getPassword());
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
    @Transactional
    void zeroBikesRentedTest()
    {
        var result = bikeMainService.getBikesRentedByUser(zeroBikesUser.getLogin());
        Assertions.assertNotNull(result);
        var resultTab = result.toArray();
        Assertions.assertArrayEquals(new BikeDTO[]{}, resultTab);
    }

    @Test
    @Order(3)
    @Transactional
    void oneBikeRentedTest()
    {
        var bikeDTO=bikeMainService.addNewBike(stationOneId);
        var t =rentalMainService.rentBike(bikeDTO.getId(), "oneBike" );
        var result = bikeMainService.getBikesRentedByUser("oneBike");
        Assertions.assertNotNull(result);
        var resultTab = result.toArray();
        bikeDTO.setStation(null);
        Assertions.assertArrayEquals(new BikeDTO[]{ bikeDTO}, resultTab);
    }
}
