package com.example.bikeRenting.repository;


import com.example.bikeRenting.model.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BikeRepository extends JpaRepository<Bike, Long> {
    Set<Bike> getAllByIsBlocked(boolean blocked);
}
