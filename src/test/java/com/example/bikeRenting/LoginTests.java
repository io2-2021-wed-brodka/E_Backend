package com.example.bikeRenting;

import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.user.LoginMainService;
import configuration.FlywayMigrationConfig;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;


@SpringBootTest
@ActiveProfiles("tests")
@Import(FlywayMigrationConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginTests {

    @Autowired
    private LoginMainService loginMainService;

    @Autowired
    private FlywayMigrationStrategy strategy;

    @BeforeAll
    void prepareInstance() {
        strategy.migrate(Flyway.configure().baselineOnMigrate(true).dataSource("jdbc:mysql://localhost:1144/dbo","renting","NiezleHaslo123!").load());
    }

    @Test
    @Order(-1)
    void contextLoads() {
    }

    @Test
    @Order(0)
    void testMigratedUserLogin() {
        UserDTO stefanoDTO = new UserDTO();
        stefanoDTO.setName("stefano");
        stefanoDTO.setPassword("stefano");
        Assertions.assertDoesNotThrow(()->loginMainService.login(stefanoDTO));
    }

    @Test
    @Order(1)
    void registerNewUser() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setName("Zaphod");
        newUserDTO.setPassword("Beeblebrox");
        newUserDTO.setId((long)2);
        Assertions.assertDoesNotThrow(()->loginMainService.register(newUserDTO));
    }

    @Test
    @Order(2)
    void loginNewUser() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setName("Zaphod");
        newUserDTO.setPassword("Beeblebrox");
        newUserDTO.setId((long)2);
        Assertions.assertDoesNotThrow(()->loginMainService.login(newUserDTO));
    }

    @Test
    @Order(3)
    void userNotInDB() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setName("Ford");
        newUserDTO.setPassword("Prefect");
        newUserDTO.setId((long)3);
        Assertions.assertThrows(RuntimeException.class, ()->loginMainService.login(newUserDTO));
    }

    @Test
    @Order(4)
    void wrongPassword() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setName("Zaphod");
        newUserDTO.setPassword("Zaphod");
        newUserDTO.setId((long)3);
        Assertions.assertThrows(RuntimeException.class, ()->loginMainService.login(newUserDTO), "Wrong password provided");
    }

    @Test
    @Order(5)
    void nullLogin() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setName(null);
        newUserDTO.setPassword("Zaphod");
        newUserDTO.setId((long)1);
        Assertions.assertThrows(RuntimeException.class, ()->loginMainService.login(newUserDTO));
    }

    @Test
    @Order(6)
    void nullPassword() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setName("stefano");
        newUserDTO.setPassword(null);
        newUserDTO.setId((long)1);
        Assertions.assertThrows(RuntimeException.class, ()->loginMainService.login(newUserDTO));
    }
}
