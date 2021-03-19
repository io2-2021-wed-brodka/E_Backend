package com.example.bikeRenting.repository;


import com.example.bikeRenting.model.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, Long> {
}
