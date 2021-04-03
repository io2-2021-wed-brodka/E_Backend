package com.example.bikeRenting.service.authentication;

import com.example.bikeRenting.dto.LoginResponseDTO;
import com.example.bikeRenting.dto.LoginRequestDTO;
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
    public LoginResponseDTO login(LoginRequestDTO request) {
        var userDetails = userService.loadUserByUsername(request.getLogin());

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
    public LoginResponseDTO register(LoginRequestDTO request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        userService.register(request);

        var response = new LoginResponseDTO();
        response.setToken(authenticationService.createJWT(request.getLogin()));
        response.setRole("user");
        return response;
    }
}
