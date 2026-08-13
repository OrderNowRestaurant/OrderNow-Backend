package ordernow.backend.ordernow_backend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dish")
    private Long idDish;

    @Column(name = "name")
    public String name;

    @Column(name = "time")
    public int time;

    @Column(name = "price")
    public float price;

    @Column(name = "description")
    public String description;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_category")
    public Category category;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    public Restaurant restaurant;

    public Dish(Restaurant restaurant, Category category, String name, String description, float price, int time) {
        this.restaurant = restaurant;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.time = time;
    }
}
