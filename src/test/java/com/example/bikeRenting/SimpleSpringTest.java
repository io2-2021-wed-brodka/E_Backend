package com.example.bikeRenting;

import com.example.bikeRenting.dto.LoginResponseDTO;
import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.user.LoginMainService;
import configuration.FlywayMigrationConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.PrepareTestInstance;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ActiveProfiles("tests")
@Import(FlywayMigrationConfig.class)
@Transactional
class SimpleSpringTest {

    @Autowired
    private LoginMainService loginMainService;

    @Autowired
    private FlywayMigrationStrategy strategy;

    @PrepareTestInstance
    void prepareInstance() {

    }

    @Test
    void contextLoads() {
    }

    @Test
    void testMigrationUserLogin() {
        UserDTO stefanoDTO = new UserDTO();
        stefanoDTO.setName("stefano");
        stefanoDTO.setPassword("NiezleHaslo123!");
        Assertions.assertDoesNotThrow(()->loginMainService.login(stefanoDTO));
    }

}
