package ordernow.backend.ordernow_backend.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "created_at")
    public LocalDateTime created_at;

    @Column(name = "id_role")
    public Integer idRole;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.created_at = LocalDateTime.now();
    }

    public User() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return this.username;
    } 

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_RESTAURANT"));
    }
}
