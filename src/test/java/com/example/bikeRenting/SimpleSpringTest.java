package com.example.bikeRenting;

import com.example.bikeRenting.dto.LoginResponseDTO;
import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.user.LoginMainService;
import configuration.FlywayMigrationConfig;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleSpringTest {

    @Autowired
    private LoginMainService loginMainService;

    @Autowired
    private FlywayMigrationStrategy strategy;

    @BeforeAll
    void prepareInstance() {
        strategy.migrate(Flyway.configure().baselineOnMigrate(true).dataSource("jdbc:mysql://localhost:1144/dbo","root","").load());
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testMigrationUserLogin() {
        UserDTO stefanoDTO = new UserDTO();
        stefanoDTO.setName("stefano");
        stefanoDTO.setPassword("stefano");
        Assertions.assertDoesNotThrow(()->loginMainService.login(stefanoDTO));
    }

}
