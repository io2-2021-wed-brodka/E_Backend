package com.example.bikeRenting.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
}
