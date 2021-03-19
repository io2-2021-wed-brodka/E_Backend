package com.example.bikeRenting.repository;


import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.BikeStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeStationRepository extends JpaRepository<BikeStation, Long> {
}
