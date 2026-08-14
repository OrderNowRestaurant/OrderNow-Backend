package ordernow.backend.ordernow_backend.entities;

import java.util.Collection;

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
@Table(name = "order")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long idOrder;

    @Column(name = "estimated_humor")
    public Long estimatedHumor;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dish")
    public Collection<Dish> dishList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_service_table")
    public ServiceTable serviceTable;
}
