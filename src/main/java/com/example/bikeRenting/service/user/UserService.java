package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.request.user.BlockUserRequestDTO;
import com.example.bikeRenting.dto.response.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDTO> getAllUsers();

    List<UserDTO> getBlockedUsers();

    UserDTO createUser(String login, String hashedPassword);

    UserDTO deleteUser(long userId);

    UserDTO blockUser(BlockUserRequestDTO request);

    UserDTO unblockUser(Long userId);

}
