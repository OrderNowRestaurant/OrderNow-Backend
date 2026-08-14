package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.Dish;
import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.repositories.CategoryRepository;
import ordernow.backend.ordernow_backend.repositories.DishRepository;
import ordernow.backend.ordernow_backend.repositories.UserRepository;
import ordernow.backend.ordernow_backend.requests.dish.CreateDishRequest;
import ordernow.backend.ordernow_backend.requests.dish.DeleteDishRequest;
import ordernow.backend.ordernow_backend.requests.dish.UpdateDishRequest;
import ordernow.backend.ordernow_backend.responses.dish.DishResponse;

@Service
public class DishService {

    private DishRepository dishRepository;
    private AuthService authService;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public DishService(DishRepository dishRepository, AuthService authService, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.dishRepository = dishRepository;
        this.authService = authService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    public DishResponse getDishes() {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        List<Dish> dishList = dishRepository.findByRestaurant(user.getRestaurant());

        if(dishList == null || dishList.isEmpty()) {
            return new DishResponse(null, "No se ha encontrado ningún plato.");
        } else {
            return new DishResponse(dishList, "Se han encontrado platos.");
        }
    }

    public DishResponse createDish(CreateDishRequest createDishRequest) {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        Dish dish = dishRepository.findByNameAndRestaurant_IdRestaurant(createDishRequest.getName(), user.getRestaurant().getIdRestaurant());

        if(dish != null) {
            return new DishResponse(null, "El plato que se ha intentado crear ya existe.");
        } else {
            Dish newDish = new Dish(
                user.getRestaurant(),
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
    }

    public DishResponse deleteDish(DeleteDishRequest deleteDishRequest) {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();
        
        Dish dish = dishRepository.findByNameAndRestaurant_IdRestaurant(deleteDishRequest.getDishName(), user.getRestaurant().getIdRestaurant());

        if(dish == null) {
            return new DishResponse(null, "El plato que se ha intentado eliminar no existe.");

        } else {
            dishRepository.delete(dish);

            return new DishResponse(null, "Se ha eliminado el plato correctamente.");
        }
    }

    public DishResponse updateDish(UpdateDishRequest updateDishRequest) {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        Dish dish = dishRepository.findByNameAndRestaurant_IdRestaurant(updateDishRequest.getOriginalName(), user.getRestaurant().getIdRestaurant());

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
