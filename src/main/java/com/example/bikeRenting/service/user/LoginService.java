package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.response.LoginResponseDTO;
import com.example.bikeRenting.dto.response.UserDTO;

public interface LoginService {
    LoginResponseDTO login(String login, String password);
    LoginResponseDTO registerLogin(String login, String password);
    UserDTO register(String login, String password);
}
