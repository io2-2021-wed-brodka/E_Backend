package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.admin.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.example.bikeRenting.constants.RoleConstants.ADMIN;

@RestController
@Secured(ADMIN)
@RequestMapping("/admin")
@Api(description = "API for admin-related operations")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public UserDTO createAdmin(@RequestBody UserDTO user) {
        return  adminService.addRole(user.getUserName(), ADMIN);
    }

    @GetMapping
    public String verifyAuthorized() {
        return "ok";
    }
}
