package com.example.bikeRenting;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BikeRentingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BikeRentingApplication.class, args);
	}

}
