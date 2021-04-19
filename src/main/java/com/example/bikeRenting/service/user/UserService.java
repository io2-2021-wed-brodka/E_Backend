package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.response.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO createUser(String login, String hashedPassword);
    UserDTO deleteUser(long userId);
}
