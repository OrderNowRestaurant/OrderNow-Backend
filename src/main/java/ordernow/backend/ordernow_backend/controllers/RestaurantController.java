package ordernow.backend.ordernow_backend.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.requests.restaurant.CreateRestaurantRequest;
import ordernow.backend.ordernow_backend.responses.restaurant.RestaurantResponse;
import ordernow.backend.ordernow_backend.services.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    
    @PostMapping("/create")
    public RestaurantResponse createRestaurant(@RequestBody CreateRestaurantRequest restaurantRequest) {
        return this.restaurantService.save(restaurantRequest);
    }

    @GetMapping("/find")
    public RestaurantResponse getRestaurantFromUser() {
        return this.restaurantService.getResturantByUser();
    }

    @DeleteMapping("/delete")
    public RestaurantResponse deleteRestaurant() {
        return this.restaurantService.deleteRestaurantByUser();
    }
}
