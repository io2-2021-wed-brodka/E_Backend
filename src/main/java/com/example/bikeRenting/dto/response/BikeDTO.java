package com.example.bikeRenting.dto.response;

import lombok.Data;

@Data
public class BikeDTO {
    private Long id;
    private BikeStationDTO station;
}
