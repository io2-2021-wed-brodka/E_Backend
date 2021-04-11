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
public class BikeStationTests {

    @Autowired
    BikeStationMainService bikeStationMainService;

    @Test
    @Order(0)
    void createStationTest()
    {
        var result = bikeStationMainService.createBikeStation(10, "testowowa stacja 3");
        var expected = new BikeStationDTO();
        expected.setMaxBikes(10);
        expected.setName("testowa stacja 3");
        expected.setId(result.getId());
        Assertions.assertEquals(expected, result);
    }
}