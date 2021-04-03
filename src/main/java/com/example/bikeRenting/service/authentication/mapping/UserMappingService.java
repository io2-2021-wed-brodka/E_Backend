package com.example.bikeRenting.service.authentication.mapping;

import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.model.MainUserDetails;
import com.example.bikeRenting.model.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserMappingService {

    public UserDTO mapToUserDTO(User user) {
        var result = new UserDTO();
        result.setName(user.getUserName());
        result.setId(user.getId());
        return result;
    }

    public UserDetails mapToUserDetails(User user) {
        var result = new MainUserDetails();
        result.setUserName(user.getUserName());
        result.setPassword(user.getPassword());
        result.setAuthorities(user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet()));
        return result;
    }
}
