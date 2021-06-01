package com.example.bikeRenting;

import com.example.bikeRenting.dto.request.bike.ReserveBikeRequestDTO;
import com.example.bikeRenting.dto.request.login.LoginRequestDTO;
import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.dto.response.ReservedBikeDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.service.bike.BikeMainService;
import com.example.bikeRenting.service.bikestation.BikeStationMainService;
import com.example.bikeRenting.service.rental.RentalMainService;
import com.example.bikeRenting.service.reservation.BikeReservationService;
import com.example.bikeRenting.service.user.LoginMainService;
import com.example.bikeRenting.service.user.LoginService;
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
@ActiveProfiles("unit-tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BikeReservationTests {

    @Autowired
    BikeReservationService reservationService;
    @Autowired
    LoginMainService loginMainService;
    @Autowired
    UserMainService userMainService;
    @Autowired
    BikeStationMainService bikeStationMainService;
    @Autowired
    BikeMainService bikeMainService;
    @Autowired
    RentalMainService rentalMainService;

    long stationOneId;
    long bikeId;
    LoginRequestDTO zeroBikesUser = new LoginRequestDTO();
    LoginRequestDTO oneBikeUser = new LoginRequestDTO();

    @BeforeAll
    void prepareData() {
        stationOneId = Long.parseLong(bikeStationMainService.createBikeStation(200, "Testy stacja 1").getId());
        zeroBikesUser.setLogin("ReservationsZero");
        zeroBikesUser.setPassword("");
        userMainService.createUser(zeroBikesUser.getLogin(), zeroBikesUser.getPassword());
        oneBikeUser.setLogin("ReservationsOne");
        oneBikeUser.setPassword("");
        userMainService.createUser(oneBikeUser.getLogin(), oneBikeUser.getPassword());
        bikeId = Long.parseLong(bikeMainService.addNewBike(stationOneId).getId());
    }


    @Test
    @Order(0)
    void createReservationTest() {
        ReserveBikeRequestDTO reserveBikeDTO = new ReserveBikeRequestDTO();
        reserveBikeDTO.setId(Long.toString(bikeId));
        Assertions.assertDoesNotThrow(()->reservationService.reserveBike(reserveBikeDTO,zeroBikesUser.getLogin()));
    }

    @Test
    @Order(1)
    void listReservationsTest() {
        var list = reservationService.getReservedByUser(zeroBikesUser.getLogin());
        Assertions.assertEquals(1,list.getBikes().size());
    }

    @Test
    @Order(2)
    void verifyReservation() {
        Assertions.assertThrows(RuntimeException.class,()->rentalMainService.rentBike(bikeId, oneBikeUser.getLogin()));
    }

    @Test
    @Order(3)
    void cancelReservationTest() {
        Assertions.assertDoesNotThrow(()->reservationService.cancelReservation(bikeId, zeroBikesUser.getLogin()));
    }

    @Test
    @Order(4)
    void verifyCancellation1() {
        var list = reservationService.getReservedByUser(zeroBikesUser.getLogin());
        Assertions.assertEquals(0,list.getBikes().size());
    }

    @Test
    @Order(5)
    void verifyCancellation2() {
        Assertions.assertDoesNotThrow(()->rentalMainService.rentBike(bikeId, oneBikeUser.getLogin()));
    }
}
