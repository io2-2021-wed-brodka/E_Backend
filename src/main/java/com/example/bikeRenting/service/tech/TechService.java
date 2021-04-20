package com.example.bikeRenting.service.tech;

import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.model.entity.User;

public interface TechService {
    UserDTO createTech(String login, String password);
    UserDTO deleteTech(long techId);
}
