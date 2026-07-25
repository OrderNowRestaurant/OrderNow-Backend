package ordernow.backend.ordernow_backend.entities;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "id_category")
    public Category cateogory;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    public Restaurant restaurant;
}
