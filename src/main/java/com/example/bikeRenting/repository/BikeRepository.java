package com.example.bikeRenting.repository;


import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.enums.BikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BikeRepository extends JpaRepository<Bike, Long> {


    @Query("select b from Bike b where b.status = :status")
    Set<Bike> findAllByStatus(@Param("status") BikeStatus status);
//    Set<Bike> findAllByStatusAnd(@Param("status") BikeStatus status);

    @Query("select b from Bike b where b.status <> :status")
    Set<Bike> findAllNotStatus(@Param("status") BikeStatus status);

    @Query("select b from Bike b " +
            "left join b.bikeStation bs " +
            "where bs.Id = :stationId " +
            "and b.status = :status")
    List<Bike> findAllByStationIdAndStatus(@Param("stationId") Long stationId,
                                           @Param("status") BikeStatus status);
}
