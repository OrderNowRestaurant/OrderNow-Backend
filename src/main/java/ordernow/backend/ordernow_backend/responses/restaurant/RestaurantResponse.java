package ordernow.backend.ordernow_backend.responses.restaurant;

import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class RestaurantResponse extends BaseResponse{
    private Restaurant restaurant;

    public RestaurantResponse(Restaurant restaurant, String message) {
        super(message);
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
