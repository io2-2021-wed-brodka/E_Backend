package com.example.bikeRenting.api;

import com.example.bikeRenting.configuration.RestAuthenticationSuccessHandler;
import com.example.bikeRenting.constants.ProfileConstants;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock")
@Profile(ProfileConstants.PROFILE_DEV)
@Api("API dedicated to help with development")
public class MockController {

    @Autowired
    private RestAuthenticationSuccessHandler successHandler;

    @GetMapping("/{userLogin}")
    public String mockCreateJwt(@PathVariable String userLogin) {
        return successHandler.mockSuccessfulAuthentication(userLogin);
    }

}
