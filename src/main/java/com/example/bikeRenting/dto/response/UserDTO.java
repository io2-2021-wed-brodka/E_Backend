package com.example.bikeRenting.dto.response;

import com.example.bikeRenting.model.entity.UserStatus;
import lombok.Data;

@Data
public class UserDTO {

    private String id;
    private String name;
    private UserStatus status;

}
