package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.response.LoginResponseDTO;
import com.example.bikeRenting.dto.request.login.LoginRequestDTO;
import com.example.bikeRenting.service.user.LoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Api(description = "API for registering and logging")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public LoginResponseDTO register(@RequestBody LoginRequestDTO requestDTO) {
        return loginService.registerLogin(requestDTO.getLogin(), requestDTO.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO requestDTO) {
        return loginService.login(requestDTO.getLogin(), requestDTO.getPassword());
    }

    @PostMapping("/xd")
    public Principal xd(Principal principal) {
        return principal;
    }

}
