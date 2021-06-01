package com.example.bikeRenting.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDTO {
    private String id;
    private UserDTO user;
}
