package ordernow.backend.ordernow_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.requests.LoginRequest;
import ordernow.backend.ordernow_backend.requests.user.CreateUserRequest;
import ordernow.backend.ordernow_backend.requests.user.EditUserRequest;
import ordernow.backend.ordernow_backend.responses.AuthResponse;
import ordernow.backend.ordernow_backend.responses.BaseResponse;
import ordernow.backend.ordernow_backend.responses.user.UserListResponse;
import ordernow.backend.ordernow_backend.responses.user.UserResponse;
import ordernow.backend.ordernow_backend.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public UserListResponse getUser() {
        return this.userService.getUsers();
    }
    
    @PostMapping("/register")
    public BaseResponse registerUser(@RequestBody User user) {
        return this.userService.save(user);
    }
    
    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest request) throws IllegalArgumentException {
        return this.userService.login(request);
    }

    @PostMapping("/create")
    public UserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        return this.userService.createUser(createUserRequest);
    }

    @PutMapping("/change/role")
    public UserResponse switchRole(@RequestBody User user) {
        return this.userService.changeUserRole(user);
    }

    @PostMapping("/delete/{username}")
    public BaseResponse deleteUser(@PathVariable String username) {
        return this.userService.deleteUser(username);
    }

    @PutMapping("/edit")
    public UserResponse editUser(@RequestBody EditUserRequest editUserRequest) {
        return this.userService.editUser(editUserRequest);
    }
}

