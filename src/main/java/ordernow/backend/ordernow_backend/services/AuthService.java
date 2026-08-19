package ordernow.backend.ordernow_backend.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.exceptions.ResourceNotFoundException;
import ordernow.backend.ordernow_backend.repositories.UserRepository;

@Service
public class AuthService extends JwtService{
    
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * It gets the user in UserDetails or Pricn
     * @return
     */
    public Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

     /**
     * Gets the username, no matter if it is a instance of UserDetail or Principal
     * @return
     */
    public String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return principal instanceof UserDetails userDetails ? userDetails.getUsername() : principal.toString();
    }

    public User getAuthenticatedUser() {
        return userRepository.findByUsername(this.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("El usuario autenticado no ha sido encontrado"));
    }
}
