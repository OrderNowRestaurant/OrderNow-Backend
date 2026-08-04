package ordernow.backend.ordernow_backend.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends JwtService{
    

    /**
     * It gets the user in UserDetails or Pricn
     * @return
     */
    public Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

     /**
     * Gets the username, no matter if it is a instance of UserDetial or Principal
     * @return
     */
    public String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return principal instanceof UserDetails userDetails ? userDetails.getUsername() : principal.toString();
    }
}
