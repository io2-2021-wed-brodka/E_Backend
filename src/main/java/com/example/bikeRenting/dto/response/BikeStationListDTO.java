package com.example.bikeRenting.dto.response;

import com.example.bikeRenting.model.entity.BikeStation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class BikeStationListDTO {
    private Collection<BikeStationDTO> stations;
}