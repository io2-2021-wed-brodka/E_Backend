package com.example.bikeRenting;


import com.example.bikeRenting.dto.request.login.LoginRequestDTO;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.enums.BikeStatus;
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
            stationOneId = bikeStationMainService.createBikeStation(200, "Testy stacja 1").getId();
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
        stationDTO.setMaxBikes(200);
        stationDTO.setStatus(BikeStation.BikeStationState.Working);
        expected.setStation(stationDTO);
        expected.setId(result.getId()); //czy +1?
        expected.setStatus(BikeStatus.ACTIVE);
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
    void blockBikeTest()
    {
        var expected = bikeMainService.addNewBike(stationOneId);
        var result = bikeMainService.blockBike(expected.getId());
        expected.setStatus(BikeStatus.BLOCKED);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @Order(4)
    void unblockBikeTest()
    {
        var expected = bikeMainService.addNewBike(stationOneId);
        bikeMainService.blockBike(expected.getId());
        var result = bikeMainService.unBlockBike(expected.getId());
        Assertions.assertEquals(expected, result);
    }

    @Test
    @Order(5)
    void deleteBikeTest()
    {
        var expected = bikeMainService.addNewBike(stationOneId);
        var result = bikeMainService.deleteBike(expected.getId());
        Assertions.assertThrows(RuntimeException.class, ()->bikeMainService.deleteBike(expected.getId()));
        Assertions.assertEquals(expected,result);
    }
}
