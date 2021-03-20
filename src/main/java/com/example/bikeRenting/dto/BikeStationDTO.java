package com.example.bikeRenting.dto;

import com.example.bikeRenting.model.entity.Bike;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class BikeStationDTO {

    private Long Id;

//  private BikeStationState bikeStationState;

    private String locationName;
    private int maxBikes;

    private List<BikeDTO> bikes;
}
