package com.example.bikeRenting.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MalfunctionDTO {
    private long id;
    private long bikeId;
    private String description;
    private long reportingUserId;
}
