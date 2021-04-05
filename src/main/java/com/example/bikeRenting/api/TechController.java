package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.admin.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.bikeRenting.constants.RoleConstants.ADMIN;
import static com.example.bikeRenting.constants.RoleConstants.TECH;

@RestController
@Secured({ADMIN, TECH})
@RequestMapping("/techs")
@Api(description = "API for operations connected with Tech")
public class TechController {
    private final AdminService adminService;

    @Autowired
    public TechController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @Secured(ADMIN)
    public UserDTO createTech(@RequestBody UserDTO user) {
        return  adminService.addRole(user.getUserName(), "ROLE_TECH");
    }
}
