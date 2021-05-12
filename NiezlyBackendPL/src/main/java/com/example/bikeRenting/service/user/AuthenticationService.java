package com.example.bikeRenting.service.user;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    String createJWT(String username);
    Authentication verifyToken(String token);
}
