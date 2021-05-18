package com.example.bikeRenting.repository;


import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.enums.BikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.websocket.server.PathParam;
import java.util.Set;

public interface BikeStationRepository extends JpaRepository<BikeStation, Long> {
    @Query("select count(b.id) from BikeStation s " +
            "join s.bikes b " +
            "where s.Id = :id")
    int getBikesCount(@PathParam("id") Long id);

    @Query("select s from BikeStation s where s.status <> :state")
    Set<BikeStation> findAllNotStatus(@Param("state") BikeStation.BikeStationState state);
}
