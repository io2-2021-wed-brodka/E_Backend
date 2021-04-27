package com.example.bikeRenting.dto.response;

import lombok.Data;

import java.util.Collection;

@Data
public class BikeListDTO {
    private Collection<BikeDTO> bikes;
}
