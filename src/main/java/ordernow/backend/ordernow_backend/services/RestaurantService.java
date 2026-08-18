package ordernow.backend.ordernow_backend.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.enums.RoleName;
import ordernow.backend.ordernow_backend.repositories.RestaurantRepository;
import ordernow.backend.ordernow_backend.repositories.RoleRepository;
import ordernow.backend.ordernow_backend.repositories.UserRepository;
import ordernow.backend.ordernow_backend.requests.restaurant.CreateRestaurantRequest;
import ordernow.backend.ordernow_backend.responses.restaurant.RestaurantResponse;

@Service
public class RestaurantService extends JwtService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final RoleRepository roleRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository, AuthService authService, RoleRepository roleRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.authService = authService;
        this.roleRepository = roleRepository;
    }

    /**
     * It creates a restaurant
     * @param restaurantRequest
     * @return
     */
    public RestaurantResponse save(CreateRestaurantRequest restaurantRequest) {
        if(checkIfUserIsAlreadyOwner(restaurantRequest) && checkIfResturantAlreadyExists(restaurantRequest.getName())) {
            return new RestaurantResponse(null, "No se ha podido crear el restaurante " + restaurantRequest.getName() + ". El restaurante ya existe o ya has creado un restaurante.");
        } else {
            Restaurant restaurant = restaurantRepository.save(new Restaurant(restaurantRequest.getName()));

            asignRestaurantToUser(restaurant);

            return new RestaurantResponse(restaurant, "Se ha creado el restaurant correctamente.");
        }
    }

    /**
     * It search for the restaurant that the user is vinculated
     * @return
     */
    public RestaurantResponse getResturantByUser() {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        return user.getRestaurant() != null ? 
            new RestaurantResponse(user.getRestaurant(), "Este usuario tiene un restaurante vinculado.")
            :
            new RestaurantResponse(null, "Este usuario no tiene ningún restaurante vinculado.");
    }

    @Transactional
    public RestaurantResponse deleteRestaurantByUser() {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        if(user.getRestaurant() != null) {
            restaurantRepository.delete(user.getRestaurant());
            user.setRestaurant(null);

            return new RestaurantResponse(null, "El restaurante ha sido borrado correctamente.");
        }

        return new RestaurantResponse(user.getRestaurant(), "El restaurante no ha podido ser borrado.");
    }


    /**
     * Check if user has already created a restaurant
     * @param restaurantRequest
     * @return
     * @throws Exception
     */
    public boolean checkIfUserIsAlreadyOwner(CreateRestaurantRequest restaurantRequest) {
        return this.restaurantRepository.existsByUserList_Username(
            authService.getUsername()
        );
    }

    public boolean checkIfResturantAlreadyExists(String restaurantName) {
        return this.restaurantRepository.findByName(restaurantName).get() != null;
    }

    public void asignRestaurantToUser(Restaurant restaurant) {
        Object principal = authService.getPrincipal();

        Optional<User> user = userRepository.findByUsername(principal instanceof UserDetails userDetails ? userDetails.getUsername() : principal.toString());

        user.get().setRestaurant(restaurant);
        
        user.get().setRole(roleRepository.findByRoleName(RoleName.MANAGER));

        userRepository.save(user.get());
    }


}