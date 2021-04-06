package com.example.bikeRenting.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class BikeStationDTO {

    @JsonAlias("stationId")
    private Long id;

//  private BikeStationState bikeStationState;

    private String name;
    private int maxBikes;
}