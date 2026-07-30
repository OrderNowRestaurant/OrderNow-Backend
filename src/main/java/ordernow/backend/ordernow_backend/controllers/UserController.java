package ordernow.backend.ordernow_backend.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.requests.LoginRequest;
import ordernow.backend.ordernow_backend.responses.AuthResponse;
import ordernow.backend.ordernow_backend.responses.BaseResponse;
import ordernow.backend.ordernow_backend.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public BaseResponse createUser(@RequestBody User user) {
        return this.userService.save(user);
    }
    
    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest request) throws IllegalArgumentException {
        return this.userService.login(request);
    }
}

