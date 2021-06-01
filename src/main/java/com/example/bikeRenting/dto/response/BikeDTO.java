package com.example.bikeRenting.dto.response;

import com.example.bikeRenting.model.entity.enums.BikeStatus;
import lombok.Data;

@Data
public class BikeDTO {
    public enum BikeStatus {
        available, rented, reserved, blocked, deleted
    }
    private Long id;
    private BikeStationDTO station;
    private BikeStatus status;

}
