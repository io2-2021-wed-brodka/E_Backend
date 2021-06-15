package com.example.bikeRenting.dto.response;

import com.example.bikeRenting.model.entity.enums.BikeStatus;
import lombok.Data;

@Data
public class BikeDTO {

    private Long id;
    private BikeStationDTO station; //opcjonalne pole według specki
    private BikeStatus status;
    //będzie trzeba dodać usera gdy admin listuje rowery np na /bikes
}
