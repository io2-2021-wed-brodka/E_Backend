package com.example.bikeRenting.dto.response;

import com.example.bikeRenting.model.entity.UserStatus;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private UserStatus status; //nie ma w specyfikacji, ale potrzebne

}
