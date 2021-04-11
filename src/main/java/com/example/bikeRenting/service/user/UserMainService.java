package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.model.entity.User;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.mapping.user.UserMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserMainService implements UserService {
    private final UserRepository userRepository;
    private final UserMappingService userMappingService;

    @Autowired
    public UserMainService(UserRepository userRepository,
                           UserMappingService userMappingService) {
        this.userRepository = userRepository;
        this.userMappingService = userMappingService;
    }

    @Override
    @Transactional
    public UserDTO createUser(String login, String hashedPassword) {
        userRepository.findByUserName(login)
                .ifPresent(s -> {
                    throw new RuntimeException("user with username " + login + " already exists");
                });

        var userEntity = new User();
        userEntity.setUserName(login);
        userEntity.setPassword(hashedPassword);
        return userMappingService.mapToUserDTO(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userMappingService.mapToUserDetails(userRepository.findByUserName(s)
                .orElseThrow(() -> new UsernameNotFoundException("user with username " + s + "doesn't exist")));
    }
}
