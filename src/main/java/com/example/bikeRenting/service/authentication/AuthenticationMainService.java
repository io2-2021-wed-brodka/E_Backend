package com.example.bikeRenting.service.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.bikeRenting.model.LazyAuthoritiesAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationMainService implements AuthenticationService {

    private final String secret;
    private final long expirationTime;
    private final UserService userService;

    public AuthenticationMainService(@Value("${jwt.secret}") String secret,
                                     @Value("${jwt.expirationTime}") long expirationTime,
                                     UserService userService) {
        this.secret = secret;
        this.expirationTime = expirationTime;
        this.userService = userService;
    }


    @Override
    public String createJWT(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
    }

    public Authentication verifyToken(String token) {
        var userName = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getSubject();
        if(userName == null) {
            return null;
        }
        return new LazyAuthoritiesAuthentication(userName, userService);
    }
}
