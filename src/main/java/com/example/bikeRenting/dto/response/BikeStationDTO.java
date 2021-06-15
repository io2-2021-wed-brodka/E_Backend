package com.example.bikeRenting.dto.response;

import com.example.bikeRenting.model.entity.BikeStation;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class BikeStationDTO {
    private Long id;
    private String name;
    private int maxBikes; //nie wiem czy ostatecznie będzie czy nie
    private BikeStation.BikeStationState status; //opcjonalne pole według specki
    //dodać activeBikesCount //opcjonalne pole według specki
}