package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.LoginRequestDTO;
import com.example.bikeRenting.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public LoginRequestDTO createAdmin(@RequestBody LoginRequestDTO user) {
        return  adminService.addRole(user.getLogin(), "ROLE_ADMIN");
    }
}
