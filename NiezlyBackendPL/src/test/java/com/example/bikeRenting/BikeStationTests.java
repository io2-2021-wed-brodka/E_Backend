package com.example.bikeRenting;

import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.service.bikestation.BikeStationMainService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("unit-tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BikeStationTests {

    @Autowired
    BikeStationMainService bikeStationMainService;

    @Test
    @Order(0)
    void createStationTest()
    {
        var result = bikeStationMainService.createBikeStation(10, "testowa stacja 3");
        var expected = new BikeStationDTO();
        expected.setMaxBikes(10);
        expected.setName("testowa stacja 3");
        expected.setId(result.getId());
        expected.setStatus(BikeStation.BikeStationState.Working);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @Order(1)
    void blockStationTest()
    {
        var org = bikeStationMainService.createBikeStation(10, "testowa stacja 4");
        var result = bikeStationMainService.blockBikeStation(org.getId());
        var expected = new BikeStationDTO();
        expected.setMaxBikes(10);
        expected.setName("testowa stacja 4");
        expected.setId(org.getId());
        expected.setStatus(BikeStation.BikeStationState.Blocked);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @Order(2)
    void unblockStationTest()
    {
        var org = bikeStationMainService.createBikeStation(10, "testowa stacja 5");
        bikeStationMainService.blockBikeStation(org.getId());
        var result = bikeStationMainService.unblockBikeStation(org.getId());
        var expected = "Station unblocked";
        Assertions.assertEquals(expected, result);
    }
}
