package com.example.bikeRenting.service.authentication;

import com.example.bikeRenting.dto.UserDTO;

public interface RoleService {
    UserDTO addRole(String userName, String roleName);
}
