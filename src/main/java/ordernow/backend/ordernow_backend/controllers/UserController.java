package ordernow.backend.ordernow_backend.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.services.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/user/register")
    public User createuser(@RequestBody User user) {
        return this.userService.save(user);
    }
    
    @PostMapping("/user/login")
    public User loginUser(@RequestBody User user) throws Exception {
        return this.userService.checkUser(user);
    }
}

