package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.authentication.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {
    private final RoleService roleService;

    @Autowired
    public AdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public UserDTO createAdmin(@RequestBody UserDTO user) {
        return  roleService.addRole(user.getName(), "ROLE_ADMIN");
    }
}
