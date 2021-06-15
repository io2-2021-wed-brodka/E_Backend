package com.example.bikeRenting.dto.response;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String role; //tylko token, ale to do ogarnięcia
}