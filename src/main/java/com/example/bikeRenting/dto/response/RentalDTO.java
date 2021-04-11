package com.example.bikeRenting.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDTO {
    private Long id;
    private UserDTO user;
}
