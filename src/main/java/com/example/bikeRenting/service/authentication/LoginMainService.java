package com.example.bikeRenting.service.authentication;

import com.example.bikeRenting.dto.LoginResponseDTO;
import com.example.bikeRenting.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginMainService implements LoginService{

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginMainService(AuthenticationService authenticationService, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO login(UserDTO request) {
        var userDetails = userService.loadUserByUsername(request.getName());

        if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Wrong password provided");
        }

        var response = new LoginResponseDTO();
        response.setToken(authenticationService.createJWT(userDetails.getUsername()));
        var role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", "").toLowerCase())
                .orElse("user");
        response.setRole(role);
        return response;
    }

    @Override
    public LoginResponseDTO registerLogin(UserDTO user) {
        register(user);

        var response = new LoginResponseDTO();
        response.setToken(authenticationService.createJWT(user.getName()));
        response.setRole("user");
        return response;
    }

    @Override
    public UserDTO register(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.createUser(user);
    }
}
