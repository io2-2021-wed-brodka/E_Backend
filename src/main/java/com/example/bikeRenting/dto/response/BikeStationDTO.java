package com.example.bikeRenting.dto.response;

import com.example.bikeRenting.model.entity.BikeStation;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class BikeStationDTO {

    public enum StationState {
        active, blocked, deleted
    }

    private String id;
    private String name;
    private int bikesLimit;
    private int activeBikesCount;
    private StationState status;
}