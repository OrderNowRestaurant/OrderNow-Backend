package ordernow.backend.ordernow_backend.entities;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    public LocalDateTime created_at;

    @OneToMany
    @JoinColumn(name = "id_service_table")
    public Collection<ServiceTable> serviceTableList;


    public Restaurant(String name, String password) {
        this.name = name;
        this.password = password;
        this.created_at = LocalDateTime.now();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
