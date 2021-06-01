package com.example.bikeRenting.dto.request.malfunction;

import lombok.Data;

@Data
public class AddMalfunctionRequestDTO {
    private long id;
    private String description;
}
