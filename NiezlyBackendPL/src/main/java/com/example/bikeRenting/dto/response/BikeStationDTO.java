package com.example.bikeRenting.dto.response;

import com.example.bikeRenting.model.entity.BikeStation;
import lombok.Data;

@Data
public class BikeStationDTO {
    private Long id;
    private String name;
    private int maxBikes;
    private BikeStation.BikeStationState status;
}
