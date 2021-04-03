package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.UserDTO;
import com.example.bikeRenting.service.TechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured({"ROLE_ADMIN","ROLE_TECH"})
@RequestMapping("/techs")
public class TechController {
    private final TechService techService;

    @Autowired
    public TechController(TechService techService) {
        this.techService = techService;
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public UserDTO createTech(@RequestBody UserDTO user) {
        return  techService.createTech(user);
    }
}
