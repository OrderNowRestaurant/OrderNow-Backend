package ordernow.backend.ordernow_backend.entities;

import org.springframework.context.annotation.Primary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_table")
public class ServiceTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service_table")
    private Long idServiceTable;

    @Column(name = "name")
    public String name;

    @Column(name = "created_at")
    public String created_at;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    public ServiceTable(String name, Restaurant restaurant) {
        this.name = name;
        this.restaurant = restaurant;
    }
}
