package com.example.bikeRenting.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    @JsonAlias({"username", "login", "userName"})
    private String name;

    private String password;
}
