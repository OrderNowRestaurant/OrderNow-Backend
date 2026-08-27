package ordernow.backend.ordernow_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.Dish;
import ordernow.backend.ordernow_backend.entities.Restaurant;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {
    List<Dish> findByRestaurant(Restaurant restaurant);

    @Query("SELECT d FROM Dish d WHERE d.name = :name AND d.restaurant.idRestaurant = :restaurantId")
    Optional<Dish> findByNameAndRestaurantId(@Param("name") String name, @Param("restaurantId") Long restaurantId);
}