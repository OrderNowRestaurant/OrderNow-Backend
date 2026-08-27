package ordernow.backend.ordernow_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.Order;
import ordernow.backend.ordernow_backend.entities.Restaurant;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByRestaurant_IdRestaurant(Long restaurantId);
    List<Order> findByRestaurant(Restaurant restaurant);
    Optional<Order> findByIdOrderAndRestaurant_IdRestaurant(Long orderId, Long restaurantId);
}
