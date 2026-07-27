package ordernow.backend.ordernow_backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.repositories.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        //restaurant.setPassword(getEncryptedPassword(restaurant.getPassword()));

        return restaurantRepository.save(restaurant);
    }

    //public Restaurant checkRestaurant(Optional<Restaurant> restaurant) throws Exception {}

    //public String getEncryptedPassword(String password) {return this.passwordEncoder.encode(password); }
}