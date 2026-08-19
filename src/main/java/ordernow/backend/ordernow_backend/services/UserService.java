package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.dtos.UserResponseDTO;
import ordernow.backend.ordernow_backend.entities.Role;
import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.enums.RoleName;
import ordernow.backend.ordernow_backend.repositories.RoleRepository;
import ordernow.backend.ordernow_backend.repositories.UserRepository;
import ordernow.backend.ordernow_backend.requests.LoginRequest;
import ordernow.backend.ordernow_backend.requests.user.CreateUserRequest;
import ordernow.backend.ordernow_backend.requests.user.EditUserRequest;
import ordernow.backend.ordernow_backend.responses.AuthResponse;
import ordernow.backend.ordernow_backend.responses.BaseResponse;
import ordernow.backend.ordernow_backend.responses.user.UserListResponse;
import ordernow.backend.ordernow_backend.responses.user.UserResponse;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, JwtService jwtService, 
                        AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder,
                        RoleRepository roleRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authService = authService;
    }

    public UserListResponse getUsers() {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        List<User> userList = userRepository.findByRestaurant(user.getRestaurant());

        List<UserResponseDTO> dtoList = userList.stream()
            .map(UserResponseDTO::fromEntity) 
            .toList();

        return new UserListResponse(dtoList, "Los trabajadores se han obtenido correctamente.");
    }

    public BaseResponse save(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return new BaseResponse("El usuario se ha registrado correctamente. Pruebe a iniciar sesión.");
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

        } catch (BadCredentialsException e) {

            throw new BadCredentialsException("Usuario o contraseña incorrectos");

        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña incorrectos"));

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getUsername(), "Has iniciado sesión correctamente.");
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        RoleName roleEnum = RoleName.valueOf(createUserRequest.getRoleName().toUpperCase());

        User newUser = userRepository.save(new User(
            createUserRequest.getUsername(), 
            passwordEncoder.encode(createUserRequest.getPassword()),
            roleRepository.findByRoleName(roleEnum),
            user.getRestaurant()
        ));

        return new UserResponse("Se ha creado el usuario correctamente", UserResponseDTO.fromEntity(newUser));
    }

    public UserResponse changeUserRole(User user) {
        Role actualRole = this.roleRepository.findByRoleName(user.getRole().getRoleName());

        if(actualRole.getRoleName() == RoleName.MANAGER) {
            user.setRole(this.roleRepository.findByRoleName(RoleName.WORKER));
        } else {
            user.setRole(this.roleRepository.findByRoleName(RoleName.MANAGER));
        }

        userRepository.save(user);

        return new UserResponse("Rol cambiado correctamente.", UserResponseDTO.fromEntity(user));
    }

    public BaseResponse deleteUser(String username) {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        User userToDelete = userRepository.findByUsernameAndRestaurant(username, user.getRestaurant()).get();

        userRepository.delete(userToDelete);

        return new BaseResponse("El usuario " + username + " se ha borrado correctamente.");
    } 

    public UserResponse editUser(EditUserRequest editUserRequest) {
        User actualUser = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        User userToEdit = userRepository.findByUsernameAndRestaurant(editUserRequest.getOriginalUsername(), actualUser.getRestaurant()).get();

        userToEdit.setPassword(passwordEncoder.encode(editUserRequest.getPassword()));
        userToEdit.setRole(roleRepository.findByRoleName(RoleName.valueOf(editUserRequest.getRoleName())));
        userToEdit.setUsername(editUserRequest.getUsername());

        userRepository.save(userToEdit);

        return new UserResponse("El usuario se ha editado correctamente", UserResponseDTO.fromEntity(userToEdit));
    } 
}