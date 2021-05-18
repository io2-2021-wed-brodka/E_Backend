package com.example.bikeRenting.service.tech;

import com.example.bikeRenting.constants.RoleConstants;
import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.exception.EntityNotFoundException;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.User;
import com.example.bikeRenting.model.entity.UserStatus;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.mapping.user.UserMappingService;
import com.example.bikeRenting.service.user.LoginService;
import com.example.bikeRenting.service.user.RoleService;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechMainService implements TechService {

    private final LoginService loginService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserMappingService userMappingService;

    public TechMainService(LoginService loginService, RoleService roleService, UserRepository userRepository, UserMappingService userMappingService) {
        this.loginService = loginService;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.userMappingService = userMappingService;
    }

    @Override
    public List<UserDTO> listAll() {
        return  userRepository.findAllTechs().stream()
                .filter(x->UserStatus.DELETED!=x.getStatus())
                .map(userMappingService::mapToUserDTO)
                .collect(Collectors.toList());
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

        if(checkIfDeleted(user)) {
            throw new EntityNotFoundException("Tech with id " + techId + " does not exist.");
        }

        user.setRoles(null);
        return loginService.deleteUser(techId);
    }

    private boolean checkIfDeleted(User user) {
        return UserStatus.DELETED == user.getStatus();
    }
}
