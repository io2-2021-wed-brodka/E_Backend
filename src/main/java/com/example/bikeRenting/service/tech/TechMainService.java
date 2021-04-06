package com.example.bikeRenting.service.tech;

import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.user.LoginService;
import com.example.bikeRenting.service.user.RoleService;
import org.springframework.stereotype.Service;

@Service
public class TechMainService implements TechService {

    private final LoginService loginService;
    private final RoleService roleService;

    public TechMainService(LoginService loginService, RoleService roleService) {
        this.loginService = loginService;
        this.roleService = roleService;
    }

    @Override
    public UserDTO createTech(UserDTO user) {
        var registeredUser =  loginService.register(user);
        return roleService.addRole(registeredUser.getName(), "ROLE_TECH");
    }
}
