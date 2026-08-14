package ordernow.backend.ordernow_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.Category;
import ordernow.backend.ordernow_backend.entities.Restaurant;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.restaurant IS NULL OR c.restaurant = :restaurant")
    List<Category> findByRestaurantIsNullMatchesOrRestaurant(@Param("restaurant") Restaurant restaurant);

    Category findByName(@Param("name") String name);
}
