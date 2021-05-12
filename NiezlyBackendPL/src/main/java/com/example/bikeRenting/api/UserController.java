package com.example.bikeRenting.api;

import com.example.bikeRenting.dto.request.user.BlockUserRequestDTO;
import com.example.bikeRenting.dto.response.UserDTO;
import com.example.bikeRenting.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bikeRenting.constants.RoleConstants.ADMIN;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured(ADMIN)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/blocked")
    @ResponseStatus(HttpStatus.OK)
    @Secured(ADMIN)
    public List<UserDTO> getBlockedUsers() {
        return userService.getBlockedUsers();
    }

    @PostMapping("/blocked")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(ADMIN)
    public UserDTO blockUser(@RequestBody BlockUserRequestDTO request) {
        return userService.blockUser(request);
    }

    @DeleteMapping("/blocked/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(ADMIN)
    public UserDTO unblockUser(@PathVariable Long userId) {
        return userService.unblockUser(userId);
    }

}
