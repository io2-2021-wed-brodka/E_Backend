package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.service.user.RoleService;
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
    private final RoleService roleService;

    @Autowired
    public AdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public UserDTO createAdmin(@RequestBody UserDTO user) {
        return  roleService.addRole(user.getName(), ADMIN);
    }

    @GetMapping
    public String verifyAuthorized() {
        return "ok";
    }
}
