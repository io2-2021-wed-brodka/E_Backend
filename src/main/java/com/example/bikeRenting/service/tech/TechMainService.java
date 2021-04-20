package com.example.bikeRenting.service.tech;

import com.example.bikeRenting.constants.RoleConstants;
import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.user.LoginService;
import com.example.bikeRenting.service.user.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TechMainService implements TechService {

    private final LoginService loginService;
    private final RoleService roleService;
    private final UserRepository userRepository;

    public TechMainService(LoginService loginService, RoleService roleService, UserRepository userRepository) {
        this.loginService = loginService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createTech(String login, String password) {
        var registeredUser =  loginService.register(login, password);
        return roleService.addRole(registeredUser.getName(), RoleConstants.TECH);
    }

    @Override
    @Transactional
    public  UserDTO deleteTech(long techId) {
        var user = userRepository.findById(techId)
                .orElseThrow(()-> new RuntimeException( "Tech with id " + techId + " does not exist."));
        user.setRoles(null);
        return loginService.deleteUser(techId);
    }
}
