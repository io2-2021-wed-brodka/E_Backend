package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO register(UserDTO user);
}
