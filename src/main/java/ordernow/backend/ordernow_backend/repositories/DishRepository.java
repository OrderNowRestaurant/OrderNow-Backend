package ordernow.backend.ordernow_backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.Dish;
import ordernow.backend.ordernow_backend.entities.Restaurant;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {
    List<Dish> findByRestaurant(Restaurant restaurant);

    Dish findByNameAndRestaurant_IdRestaurant(String name, Long idRestaurant);
}