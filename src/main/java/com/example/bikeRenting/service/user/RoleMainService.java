package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.model.entity.Role;
import com.example.bikeRenting.repository.RoleRepository;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.mapping.user.UserMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RoleMainService implements RoleService {

    private final UserRepository userRepository;
    private final UserMappingService userMappingService;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleMainService(UserRepository userRepository,
                           UserMappingService userMappingService,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMappingService = userMappingService;
        this.roleRepository = roleRepository;
    }


    @Override
    public UserDTO addRole(String userName, String roleName) {
        var user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + userName + " doesn't exist"));

        var roleOptional = roleRepository.findByName(roleName);

        if(roleOptional.isEmpty()) {
            var role = new Role();
            role.setName(roleName);
            role.setUsers(Stream.of(user).collect(Collectors.toSet()));
            roleRepository.save(role);
            return userMappingService.mapToUserDTO(user);
        }

        var role = roleOptional.get();
        role.getUsers().add(user);
        roleRepository.save(role);

        return userMappingService.mapToUserDTO(user);
    }
}
