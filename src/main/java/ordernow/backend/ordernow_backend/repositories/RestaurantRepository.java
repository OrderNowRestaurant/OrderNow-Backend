package ordernow.backend.ordernow_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import ordernow.backend.ordernow_backend.entities.Restaurant;

@org.springframework.stereotype.Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
