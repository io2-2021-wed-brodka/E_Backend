package com.example.bikeRenting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.PrepareTestInstance;

@SpringBootTest
@ActiveProfiles("tests")
class SimpleSpringTest {

    @PrepareTestInstance
    void prepareInstance() {

    }

    @Test
    void contextLoads() {
    }

}
