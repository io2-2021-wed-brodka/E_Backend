package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.request.user.BlockUserRequestDTO;
import com.example.bikeRenting.dto.response.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<UserDTO> getAllUsers();

    List<UserDTO> getAllOnlyUsers();

    List<UserDTO> getBlockedUsers();

    UserDTO createUser(String login, String hashedPassword);

    UserDTO deleteUser(long userId);

    Optional<UserDTO> findByUsername(String username);

    UserDTO blockUser(BlockUserRequestDTO request);

    UserDTO unblockUser(Long userId);
}
