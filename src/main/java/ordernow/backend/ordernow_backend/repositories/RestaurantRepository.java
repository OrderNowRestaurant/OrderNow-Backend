package ordernow.backend.ordernow_backend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ordernow.backend.ordernow_backend.entities.Restaurant;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Optional<Restaurant> findByName(String name);

    boolean existsByUserList_Username(String username);

    Optional<Restaurant> findByUserList_Username(String username);
}
