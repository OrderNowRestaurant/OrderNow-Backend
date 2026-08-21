package ordernow.backend.ordernow_backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.Order;
import ordernow.backend.ordernow_backend.entities.Restaurant;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByRestaurant(Restaurant restaurant);
}
