package com.example.bikeRenting.repository;


import com.example.bikeRenting.model.entity.BikeStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;

public interface BikeStationRepository extends JpaRepository<BikeStation, Long> {
    @Query("select count(b.id) from BikeStation s " +
            "join s.bikes b " +
            "where s.Id = :id")
    int getBikesCount(@PathParam("id") Long id);
}
