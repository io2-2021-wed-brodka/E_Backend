package com.example.bikeRenting.dto.response;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private Boolean blocked;

}
