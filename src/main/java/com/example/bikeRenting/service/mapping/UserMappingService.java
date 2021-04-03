package com.example.bikeRenting.service.mapping;

import com.example.bikeRenting.dto.LoginRequestDTO;
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
    public LoginRequestDTO mapToUserDTO(User user) {
        var result = new LoginRequestDTO();
        result.setLogin(user.getUserName());
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
