package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.request.user.BlockUserRequestDTO;
import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.exception.EntityNotFoundException;
import com.example.bikeRenting.exception.UserAlreadyBlockedException;
import com.example.bikeRenting.exception.UserNotBlockedException;
import com.example.bikeRenting.model.entity.User;
import com.example.bikeRenting.model.entity.UserStatus;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.mapping.user.UserMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> userMappingService.mapToUserDTO(u))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getBlockedUsers() {
        return userRepository.findBlocked().stream()
                .map(u -> userMappingService.mapToUserDTO(u))
                .collect(Collectors.toList());
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

    @Override
    public UserDTO deleteUser(long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user with id " + userId + " not found"));
        userRepository.deleteById(userId);
        return userMappingService.mapToUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO blockUser(BlockUserRequestDTO request) {
        var user = userRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (UserStatus.BLOCKED == user.getStatus()) {
            throw new UserAlreadyBlockedException("User already blocked");
        }

        user.setStatus(UserStatus.BLOCKED);

        return userMappingService.mapToUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO unblockUser(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (UserStatus.ACTIVE == user.getStatus()) {
            throw new UserNotBlockedException("User is not blocked");
        }

        user.setStatus(UserStatus.ACTIVE);
        return userMappingService.mapToUserDTO(user);
    }
}
