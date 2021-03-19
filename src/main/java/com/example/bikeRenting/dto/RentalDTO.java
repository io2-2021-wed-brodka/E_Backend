package com.example.bikeRenting.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDTO {
    private Long id;
    private BikeDTO bike;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
