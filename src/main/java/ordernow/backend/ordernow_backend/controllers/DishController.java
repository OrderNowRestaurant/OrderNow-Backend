package ordernow.backend.ordernow_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.requests.dish.CreateDishRequest;
import ordernow.backend.ordernow_backend.requests.dish.DeleteDishRequest;
import ordernow.backend.ordernow_backend.requests.dish.UpdateDishRequest;
import ordernow.backend.ordernow_backend.responses.dish.DishResponse;
import ordernow.backend.ordernow_backend.services.DishService;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    private DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/get")
    public DishResponse getDishes() {
        return this.dishService.getDishes();
    }

    @PostMapping("/create")
    public DishResponse createDish(@RequestBody CreateDishRequest createDishRequest) {
        return this.dishService.createDish(createDishRequest);
    }

    @PostMapping("/delete")
    public DishResponse deleteDish(@RequestBody DeleteDishRequest deleteDishRequest) {
        return this.dishService.deleteDish(deleteDishRequest);
    }

    @PutMapping("/edit")
    public DishResponse updateDish(@RequestBody UpdateDishRequest updateDishRequest) {
        return this.dishService.updateDish(updateDishRequest);
    }
}