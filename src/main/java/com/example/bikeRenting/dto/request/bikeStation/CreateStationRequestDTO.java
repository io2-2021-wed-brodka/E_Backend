package com.example.bikeRenting.dto.request.bikeStation;

import lombok.Data;

@Data
public class CreateStationRequestDTO {
    private String name;
    private Integer maxBikes; //zmiana nazwy na bikesLimit
}
