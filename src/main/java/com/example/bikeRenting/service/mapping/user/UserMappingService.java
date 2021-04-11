package com.example.bikeRenting.service.mapping.user;

import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.model.MainUserDetails;
import com.example.bikeRenting.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class UserMappingService {
    public UserDTO mapToUserDTO(User user) {
        var result = new UserDTO();
        result.setId(user.getId());
        result.setName(user.getUserName());
        return result;
    }

    public UserDetails mapToUserDetails(User user) {
        var result = new MainUserDetails();
        result.setUserName(user.getUserName());
        result.setPassword(user.getPassword());
        result.setAuthorities(user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet()));

        result.getAuthorities().forEach(a -> {log.info(a.getAuthority());});
        return result;
    }
}
