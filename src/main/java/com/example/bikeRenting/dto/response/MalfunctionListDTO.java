package com.example.bikeRenting.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class MalfunctionListDTO {
    private Collection<MalfunctionDTO> malfunctions;
}
