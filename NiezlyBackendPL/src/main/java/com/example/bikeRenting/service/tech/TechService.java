package com.example.bikeRenting.service.tech;

import com.example.bikeRenting.dto.response.UserDTO;

public interface TechService {
    UserDTO createTech(String login, String password);
    UserDTO deleteTech(long techId);
}
