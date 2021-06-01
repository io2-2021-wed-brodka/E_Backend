package com.example.bikeRenting.api;

import com.example.bikeRenting.constants.RoleConstants;
import com.example.bikeRenting.dto.request.malfunction.AddMalfunctionRequestDTO;
import com.example.bikeRenting.dto.response.MalfunctionDTO;
import com.example.bikeRenting.dto.response.MalfunctionListDTO;
import com.example.bikeRenting.service.malfunction.MalfunctionService;
import io.swagger.annotations.Api;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/malfunctions")
@Api(description = "API for managing bike malfunctions")
public class MalfunctionController {

    private final MalfunctionService malfunctionService;

    public MalfunctionController(MalfunctionService malfunctionService) {
        this.malfunctionService = malfunctionService;
    }

    @GetMapping()
    @Secured({RoleConstants.TECH, RoleConstants.ADMIN})
    public MalfunctionListDTO getAllMalfunctions() {
        return malfunctionService.getAllMalfunctions();
    }

    @PostMapping()
    public MalfunctionDTO addNewMalfunction(@RequestBody AddMalfunctionRequestDTO addMalfunctionRequestDTO, Principal principal) {
        return malfunctionService.addNewMalfunction(addMalfunctionRequestDTO, principal.getName());
    }

    @DeleteMapping("/{malfunctionId}")
    @Secured({RoleConstants.TECH, RoleConstants.ADMIN})
    public void deleteMalfunction(@PathVariable Long malfunctionId) {
        malfunctionService.deleteMalfunction(malfunctionId);
    }

}
