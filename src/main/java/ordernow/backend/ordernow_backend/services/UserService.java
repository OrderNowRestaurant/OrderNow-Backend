package ordernow.backend.ordernow_backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        user.setPassword(getEncryptedPassword(user.getPassword()));

        return userRepository.save(user);
    }

    public User checkUser(User user) throws Exception {
        Optional<User> storedUser = userRepository.findByUsername(user.getUsername());

        boolean passwordCheck = this.passwordEncoder.matches(user.getPassword(), storedUser.get().getPassword());

        if(!passwordCheck) {
            throw new Exception("Contraseña incorrecta");
        }

        return storedUser.get();
    }

    public String getEncryptedPassword(String password) {
        return this.passwordEncoder.encode(password);
    }
}
