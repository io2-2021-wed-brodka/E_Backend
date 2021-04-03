package com.example.bikeRenting.service.authentication;

import com.example.bikeRenting.dto.LoginResponseDTO;
import com.example.bikeRenting.dto.LoginRequestDTO;

public interface LoginService {
    LoginResponseDTO login(LoginRequestDTO user);
    LoginResponseDTO register(LoginRequestDTO user);
}
