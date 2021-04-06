package com.example.bikeRenting.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String role;
}