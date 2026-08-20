package ordernow.backend.ordernow_backend.services;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.enums.RoleName;
import ordernow.backend.ordernow_backend.exceptions.DuplicateResourceException;
import ordernow.backend.ordernow_backend.exceptions.NotEnoughPermissionsException;
import ordernow.backend.ordernow_backend.exceptions.ResourceNotFoundException;
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
            throw new DuplicateResourceException("No se ha podido crear el restaurante " + restaurantRequest.getName() + ". El restaurante ya existe o ya has creado un restaurante.");
        }

        Restaurant restaurant = restaurantRepository.save(new Restaurant(restaurantRequest.getName()));

        asignRestaurantToUser(restaurant);

        return new RestaurantResponse(restaurant, "Se ha creado el restaurant correctamente.");
    }

    /**
     * It search for the restaurant that the user is vinculated
     * @return
     */
    public RestaurantResponse getResturantByUser() {
        if (authService.getAuthenticatedUser().getRestaurant() == null) {
            throw new ResourceNotFoundException("Este usuario no tiene ningún restaurante vinculado.");
        }

        return new RestaurantResponse(authService.getAuthenticatedUser().getRestaurant(), "Este usuario tiene un restaurante vinculado.");
    }

    @Transactional
    public RestaurantResponse deleteRestaurantByUser() {
        if (authService.getAuthenticatedUser().getRole().getRoleName() != RoleName.MANAGER) {
            throw new NotEnoughPermissionsException("No eres manager");
        }
        
        if(authService.getAuthenticatedUser().getRestaurant() == null) {
            throw new ResourceNotFoundException("El restaurante no ha podido ser borrado. El usuario actual no tiene asignado ningún restaurante.");
        }

        restaurantRepository.delete(authService.getAuthenticatedUser().getRestaurant());
        authService.getAuthenticatedUser().setRestaurant(null);

        return new RestaurantResponse(null, "El restaurante ha sido borrado correctamente.");
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
        authService.getAuthenticatedUser().setRestaurant(restaurant);
        
        authService.getAuthenticatedUser().setRole(roleRepository.findByRoleName(RoleName.MANAGER));

        userRepository.save(authService.getAuthenticatedUser());
    }
}