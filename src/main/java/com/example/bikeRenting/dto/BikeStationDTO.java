package com.example.bikeRenting.dto;

import com.example.bikeRenting.model.entity.Bike;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class BikeStationDTO {

    @JsonAlias("stationId")
    private Long id;

//  private BikeStationState bikeStationState;

    private String name;
    private int maxBikes;
}
