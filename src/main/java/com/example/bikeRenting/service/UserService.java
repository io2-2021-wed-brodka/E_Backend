package com.example.bikeRenting.service;

import com.example.bikeRenting.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO register(UserDTO user);
}
