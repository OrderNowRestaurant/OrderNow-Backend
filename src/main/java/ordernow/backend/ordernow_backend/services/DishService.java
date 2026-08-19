package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.Dish;
import ordernow.backend.ordernow_backend.exceptions.DuplicateResourceException;
import ordernow.backend.ordernow_backend.exceptions.ResourceNotFoundException;
import ordernow.backend.ordernow_backend.repositories.CategoryRepository;
import ordernow.backend.ordernow_backend.repositories.DishRepository;
import ordernow.backend.ordernow_backend.requests.dish.CreateDishRequest;
import ordernow.backend.ordernow_backend.requests.dish.DeleteDishRequest;
import ordernow.backend.ordernow_backend.requests.dish.UpdateDishRequest;
import ordernow.backend.ordernow_backend.responses.dish.DishResponse;

@Service
public class DishService {

    private DishRepository dishRepository;
    private AuthService authService;
    private CategoryRepository categoryRepository;

    public DishService(DishRepository dishRepository, AuthService authService, CategoryRepository categoryRepository) {
        this.dishRepository = dishRepository;
        this.authService = authService;
        this.categoryRepository = categoryRepository;
    }


    public DishResponse getDishes() {
        List<Dish> dishList = dishRepository.findByRestaurant(authService.getAuthenticatedUser().getRestaurant());

        if(dishList == null || dishList.isEmpty()) {
            throw new ResourceNotFoundException("No se ha encontrado ningún plato.");
        } 

        return new DishResponse(dishList, "Se han encontrado platos.");
    }

    public DishResponse createDish(CreateDishRequest createDishRequest) {
        Dish dish = dishRepository.findByNameAndRestaurant_IdRestaurant(createDishRequest.getName(), authService.getAuthenticatedUser().getRestaurant().getIdRestaurant());

        if(dish != null) {
            throw new DuplicateResourceException("El plato " + dish.name + " existe.");
        }

        Dish newDish = new Dish(
            authService.getAuthenticatedUser().getRestaurant(),
            this.categoryRepository.findByName(createDishRequest.getCategoryName()),
            createDishRequest.getName(),
            createDishRequest.getDescription(),
            createDishRequest.getPrice(),
            createDishRequest.getTime()
        );

        dishRepository.save(newDish);

        List<Dish> dishList = List.of(newDish);

        return new DishResponse(dishList, "Se ha creado el plato correctamente.");
    }

    public DishResponse deleteDish(DeleteDishRequest deleteDishRequest) {        
        Dish dish = dishRepository.findByNameAndRestaurant_IdRestaurant(deleteDishRequest.getDishName(), authService.getAuthenticatedUser().getRestaurant().getIdRestaurant());

        if(dish == null) {
            throw new ResourceNotFoundException("El plato que se ha intentado eliminar no existe.");
        } 

        dishRepository.delete(dish);

        return new DishResponse(null, "Se ha eliminado el plato correctamente.");
    }

    public DishResponse updateDish(UpdateDishRequest updateDishRequest) {
        Dish dish = dishRepository.findByNameAndRestaurant_IdRestaurant(updateDishRequest.getName(), authService.getAuthenticatedUser().getRestaurant().getIdRestaurant());

        if(dish == null) {
            throw new ResourceNotFoundException("El plato que se ha intentado eliminar no existe.");
        } 

        dish.name = updateDishRequest.getName();
        dish.description = updateDishRequest.getDescription();
        dish.price = updateDishRequest.getPrice();
        dish.time = updateDishRequest.getTime();
        dish.category = this.categoryRepository.findByName(updateDishRequest.getCategoryName());

        dishRepository.save(dish);

        List<Dish> dishList = List.of(dish);

        return new DishResponse(dishList, "Plato editado correctamente.");
    }
}
