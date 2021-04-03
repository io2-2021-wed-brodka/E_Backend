package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.LoginRequestDTO;
import com.example.bikeRenting.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured({"ROLE_ADMIN","ROLE_TECH"})
@RequestMapping("/techs")
public class TechController {
    private final AdminService adminService;

    @Autowired
    public TechController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public LoginRequestDTO createTech(@RequestBody LoginRequestDTO user) {
        return  adminService.addRole(user.getLogin(), "ROLE_TECH");
    }
}
