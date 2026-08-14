package ordernow.backend.ordernow_backend.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long idCategory;

    @Column(name = "name")
    public String name;

    @Column(name = "created_at")
    public Date created_at;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "category")
    private List<Dish> dishList;
}
