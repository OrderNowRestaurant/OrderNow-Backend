package ordernow.backend.ordernow_backend.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurant")
    private Long idRestaurant;

    @Column(name = "name")
    public String name;

    @Column(name = "created_at")
    public LocalDateTime created_at;

    @OneToMany
    @JoinColumn(name = "id_service_table")
    public Collection<ServiceTable> serviceTableList;

    @OneToMany(mappedBy = "restaurant") 
    private List<User> userList;

    public Restaurant(String name) {
        this.name = name;
        this.created_at = LocalDateTime.now();
    }

    public Restaurant() {}

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public String getName() {
        return name;
    }
}
