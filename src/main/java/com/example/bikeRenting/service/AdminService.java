package com.example.bikeRenting.service;

import com.example.bikeRenting.dto.LoginRequestDTO;

public interface AdminService {
    LoginRequestDTO addRole(String userName, String roleName);
}
