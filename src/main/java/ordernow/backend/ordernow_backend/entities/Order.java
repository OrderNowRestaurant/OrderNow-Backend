package ordernow.backend.ordernow_backend.entities;

import java.util.ArrayList;
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
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long idOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_service_table")
    public ServiceTable serviceTable;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_restaurant")
    public Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(ServiceTable serviceTable, Restaurant restaurant) {
        this.serviceTable = serviceTable;
        this.restaurant = restaurant;
    }

    public ServiceTable getServiceTable() {
        return serviceTable;
    }

    public void setServiceTable(ServiceTable serviceTable) {
        this.serviceTable = serviceTable;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
