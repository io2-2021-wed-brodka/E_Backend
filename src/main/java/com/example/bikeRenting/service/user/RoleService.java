package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.response.UserDTO;

public interface RoleService {
    UserDTO addRole(String userName, String roleName);
}
