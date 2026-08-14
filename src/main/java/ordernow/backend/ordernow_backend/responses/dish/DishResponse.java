package ordernow.backend.ordernow_backend.responses.dish;

import java.util.List;

import ordernow.backend.ordernow_backend.entities.Dish;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class DishResponse extends BaseResponse {
    private List<Dish> dishList;
    

    public DishResponse(List<Dish> dishList, String message) {
        super(message);
        this.dishList = dishList;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }
}
