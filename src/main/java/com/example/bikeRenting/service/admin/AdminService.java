package com.example.bikeRenting.service.admin;

import com.example.bikeRenting.dto.UserDTO;

public interface AdminService {
    UserDTO addRole(String userName, String roleName);
}
