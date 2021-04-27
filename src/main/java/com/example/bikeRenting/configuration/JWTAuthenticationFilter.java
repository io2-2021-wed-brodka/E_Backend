package com.example.bikeRenting.configuration;

import com.example.bikeRenting.exception.UserBlockedException;
import com.example.bikeRenting.model.entity.UserStatus;
import com.example.bikeRenting.service.user.AuthenticationService;
import com.example.bikeRenting.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthenticationService authenticationService,
                                   UserService userService) {
        super(authenticationManager);
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        var authentication = getAuthentication(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        userService.findByUsername(authentication.getName()).ifPresent(u -> {
            if (UserStatus.BLOCKED.equals(u.getStatus())) {
                throw new UserBlockedException("User is blocked");
            }
        });
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        var token = request.getHeader(TOKEN_HEADER);
        if(token == null) {
            return null;
        }
        return authenticationService.verifyToken(token.replace(TOKEN_PREFIX, ""));
    }
}
