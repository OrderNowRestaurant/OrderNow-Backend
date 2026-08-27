package ordernow.backend.ordernow_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.Order;
import ordernow.backend.ordernow_backend.entities.OrderItem;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    void deleteAllByOrder(Order order);
}