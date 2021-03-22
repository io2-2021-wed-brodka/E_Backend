package com.example.bikeRenting.configuration;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.Collections;

public class LazyAuthoritiesAuthentication implements Authentication {

    private boolean authenticated;
    private final String name;
    private final UserDetailsService userDetailsService;
    private Collection<? extends GrantedAuthority> authorities = null;

    public LazyAuthoritiesAuthentication(String name, UserDetailsService userDetailsService) {
        this.name = name;
        this.authenticated = true;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities == null)  {
            authorities = userDetailsService.loadUserByUsername(getName()).getAuthorities();
            if(authorities == null) {
                authorities = Collections.emptyList();
            }
        }
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return name;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        authenticated = b;
    }

    @Override
    public String getName() {
        return name;
    }
}
