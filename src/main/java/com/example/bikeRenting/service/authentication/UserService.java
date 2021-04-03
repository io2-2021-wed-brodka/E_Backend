package com.example.bikeRenting.service.authentication;

import com.example.bikeRenting.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO user);
}
