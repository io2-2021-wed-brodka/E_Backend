package com.example.bikeRenting.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservedBikeDTO {

    private String id;
    private BikeStationDTO station;
    private LocalDateTime reservedAt;
    private LocalDateTime reservedTill;

}
