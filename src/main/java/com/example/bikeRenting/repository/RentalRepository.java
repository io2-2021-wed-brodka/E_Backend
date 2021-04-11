package com.example.bikeRenting.repository;


import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("select r from Rental r " +
            "join r.bike b " +
            "where b.id = :bikeId " +
            "and r.endDate is null")
    Optional<Rental> getCurrentBikeRental(@PathParam("bikeId") long bikeId);

    @Query("select r from Rental r " +
            "join r.bike b " +
            "join r.user u " +
            "where u.userName = :username " +
            "and r.endDate is null")
    List<Rental> getCurrentUserRentals(@PathParam("username") String username);
}
