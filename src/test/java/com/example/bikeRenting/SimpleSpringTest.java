package com.example.bikeRenting;

import configuration.FlywayMigrationConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
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
    private FlywayMigrationStrategy strategy;

    @PrepareTestInstance
    void prepareInstance() {

    }

    @Test
    void contextLoads() {
    }

}
