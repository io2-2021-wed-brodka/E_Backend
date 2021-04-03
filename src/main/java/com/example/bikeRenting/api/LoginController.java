package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.LoginResponseDTO;
import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.authentication.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public LoginResponseDTO register(@RequestBody UserDTO user) {
        return loginService.registerLogin(user);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserDTO user) {
        return loginService.login(user);
    }

    @PostMapping("/xd")
    public Principal xd(Principal principal) {
        return principal;
    }

}
