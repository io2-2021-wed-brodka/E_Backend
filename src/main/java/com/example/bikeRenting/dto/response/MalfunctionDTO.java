package com.example.bikeRenting.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MalfunctionDTO {
    private String id;
    private String bikeId;
    private String description;
    private String reportingUserId;
}
