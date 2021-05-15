package com.example.bikeRenting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BikeListDTO {
    private Collection<BikeDTO> bikes;
}
