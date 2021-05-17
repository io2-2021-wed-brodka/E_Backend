package com.example.bikeRenting.service.bike;

import com.example.bikeRenting.dto.request.bike.ReserveBikeRequestDTO;
import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.BikeListDTO;
import com.example.bikeRenting.dto.response.ReservedBikeDTO;

import java.util.Collection;
import java.util.List;

public interface BikeService {

    Collection<BikeDTO> getBikesInStation(long stationId);

    Collection<BikeDTO> getBikesRentedByUser(String userName);

    BikeDTO addNewBike(long stationId);

    BikeDTO blockBike(long bikeId);

    BikeDTO unBlockBike(long bikeId);

    Collection<BikeDTO> getAllBlockedBikes();

    BikeDTO deleteBike(long bikeId);

    Collection<BikeDTO> findAll();

    BikeListDTO getStationActiveBikes(Long stationId);

}
