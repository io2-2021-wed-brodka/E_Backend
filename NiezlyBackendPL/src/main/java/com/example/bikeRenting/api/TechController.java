package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.dto.request.tech.CreateTechRequestDTO;
import com.example.bikeRenting.service.tech.TechService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.example.bikeRenting.constants.RoleConstants.ADMIN;
import static com.example.bikeRenting.constants.RoleConstants.TECH;

@RestController
@Secured({ADMIN, TECH})
@RequestMapping("/techs")
@Api(description = "API for operations connected with Tech")
public class TechController {
    private final TechService techService;

    @Autowired
    public TechController(TechService techService) {
        this.techService = techService;
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public UserDTO createTech(@RequestBody CreateTechRequestDTO requestDTO) {
        return techService.createTech(requestDTO.getName(), requestDTO.getPassword());
    }

    @DeleteMapping("/{id}")
    @Secured(ADMIN)
    public UserDTO deleteTech(@PathVariable long id) {
        return techService.deleteTech(id);
    }
}
