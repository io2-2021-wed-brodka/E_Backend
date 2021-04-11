package com.example.bikeRenting.service.tech;

import com.example.bikeRenting.constants.RoleConstants;
import com.example.bikeRenting.dto.response.UserDTO;
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
    public UserDTO createTech(String login, String password) {
        var registeredUser =  loginService.register(login, password);
        return roleService.addRole(registeredUser.getName(), RoleConstants.TECH);
    }
}
