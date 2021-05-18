package com.example.bikeRenting.service.tech;

import com.example.bikeRenting.dto.response.UserDTO;

import java.util.List;

public interface TechService {
    UserDTO createTech(String login, String password);
    UserDTO deleteTech(long techId);
    List<UserDTO> listAll();
}
