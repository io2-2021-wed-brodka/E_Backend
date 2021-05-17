package com.example.bikeRenting.api;

import com.example.bikeRenting.constants.RoleConstants;
import com.example.bikeRenting.dto.response.TechListDTO;
import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.dto.request.tech.CreateTechRequestDTO;
import com.example.bikeRenting.model.entity.User;
import com.example.bikeRenting.service.tech.TechService;
import com.example.bikeRenting.service.user.RoleService;
import com.example.bikeRenting.service.user.UserService;
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

    @GetMapping
    @Secured(ADMIN)
    public  TechListDTO getAllTechs() {
        return new TechListDTO(techService.listAll());
    }

    @PostMapping
    @Secured(ADMIN)
    public UserDTO createTech(@RequestBody CreateTechRequestDTO requestDTO) {
        return techService.createTech(requestDTO.getName(), requestDTO.getPassword());
    }

    @DeleteMapping("/{id}")
    @Secured(ADMIN)
    public UserDTO deleteTech(@PathVariable long id) {
        return techService.deleteTech(id);
    }
}
