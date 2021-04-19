package com.example.bikeRenting.service.user;

import com.example.bikeRenting.dto.response.LoginResponseDTO;
import com.example.bikeRenting.dto.response.UserDTO;
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
    public LoginResponseDTO login(String login, String password) {
        var userDetails = userService.loadUserByUsername(login);

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
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
    public LoginResponseDTO registerLogin(String login, String password) {
        register(login, password);

        var response = new LoginResponseDTO();
        response.setToken(authenticationService.createJWT(login));
        response.setRole("user");
        return response;
    }

    @Override
    public UserDTO register(String login, String password) {
        return userService.createUser(login, passwordEncoder.encode(password));
    }

    @Override
    public UserDTO deleteUser(long userId) {
        return userService.deleteUser(userId);
    }
}
