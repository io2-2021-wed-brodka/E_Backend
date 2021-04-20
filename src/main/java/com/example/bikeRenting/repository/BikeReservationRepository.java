package com.example.bikeRenting.repository;

import com.example.bikeRenting.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BikeReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r " +
            "left join r.user u " +
            "left join r.bike b " +
            "where u.id = :userId and b.id = :bikeId")
    Optional<Reservation> findByUserIdAndBikeId(@Param("bikeId") Long bikeId, @Param("userId") Long userId);
}
