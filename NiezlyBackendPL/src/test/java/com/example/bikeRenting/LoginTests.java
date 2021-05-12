package com.example.bikeRenting;

import com.example.bikeRenting.dto.request.login.LoginRequestDTO;
import com.example.bikeRenting.service.user.LoginMainService;
import configuration.FlywayMigrationConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("unit-tests")
@Import(FlywayMigrationConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginTests {

    @Autowired
    private LoginMainService loginMainService;

    @Autowired
    private FlywayMigrationStrategy strategy;

//    @BeforeAll
//    void prepareInstance() {
//        strategy.migrate(Flyway.configure().baselineOnMigrate(true).dataSource("jdbc:h2:mem:dbo","renting","NiezleHaslo123!").load());
//    }

    @Test
    @Order(-1)
    void contextLoads() {
    }

//    @Test
//    @Order(0)
//    void testMigratedUserLogin() {
//        var stefanoDTO = new LoginRequestDTO();
//        stefanoDTO.setLogin("stefano");
//        stefanoDTO.setPassword("stefano");
//        Assertions.assertDoesNotThrow(()->loginMainService.login(stefanoDTO.getLogin(), stefanoDTO.getPassword()));
//    }

    @Test
    @Order(1)
    void registerNewUser() {
        var newUserDTO = new LoginRequestDTO();
        newUserDTO.setLogin("Zaphod");
        newUserDTO.setPassword("Beeblebrox");
        Assertions.assertDoesNotThrow(()->loginMainService.register(newUserDTO.getLogin(), newUserDTO.getPassword()));
    }

    @Test
    @Order(2)
    void loginNewUser() {
        var newUserDTO = new LoginRequestDTO();
        newUserDTO.setLogin("Zaphod");
        newUserDTO.setPassword("Beeblebrox");
        Assertions.assertDoesNotThrow(()->loginMainService.login(newUserDTO.getLogin(), newUserDTO.getPassword()));
    }

    @Test
    @Order(3)
    void userNotInDB() {
        var newUserDTO = new LoginRequestDTO();
        newUserDTO.setLogin("Ford");
        newUserDTO.setPassword("Prefect");
        Assertions.assertThrows(RuntimeException.class, ()->loginMainService.login(newUserDTO.getLogin(), newUserDTO.getPassword()));
    }

    @Test
    @Order(4)
    void wrongPassword() {
        var newUserDTO = new LoginRequestDTO();
        newUserDTO.setLogin("Zaphod");
        newUserDTO.setPassword("Zaphod");
        Assertions.assertThrows(RuntimeException.class, ()->loginMainService.login(newUserDTO.getLogin(), newUserDTO.getPassword()), "Wrong password provided");
    }

    @Test
    @Order(5)
    void nullLogin() {
        var newUserDTO = new LoginRequestDTO();
        newUserDTO.setLogin(null);
        newUserDTO.setPassword("Zaphod");
        Assertions.assertThrows(RuntimeException.class, ()->loginMainService.login(newUserDTO.getLogin(), newUserDTO.getPassword()));
    }

    @Test
    @Order(6)
    void nullPassword() {
        var newUserDTO = new LoginRequestDTO();
        newUserDTO.setLogin("Zaphod");
        newUserDTO.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, ()->loginMainService.login(newUserDTO.getLogin(), newUserDTO.getPassword()));
    }
}
