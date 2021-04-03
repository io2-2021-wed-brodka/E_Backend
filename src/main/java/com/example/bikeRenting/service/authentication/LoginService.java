package com.example.bikeRenting.service.authentication;

import com.example.bikeRenting.dto.LoginResponseDTO;
import com.example.bikeRenting.dto.UserDTO;

public interface LoginService {
    LoginResponseDTO login(UserDTO user);
    LoginResponseDTO registerLogin(UserDTO user);
    UserDTO register(UserDTO request);
}
