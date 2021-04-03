package com.example.bikeRenting.service.authentication;

import com.example.bikeRenting.dto.LoginRequestDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void register(LoginRequestDTO user);
}
