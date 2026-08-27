package ordernow.backend.ordernow_backend.dtos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ordernow.backend.ordernow_backend.entities.Category;
import ordernow.backend.ordernow_backend.entities.Dish;

public record DishByCategoryResponseDTO(
    List<CategoryWithDishesDTO> categories
) {

    public record CategoryWithDishesDTO(
        String categoryName,
        List<Dish> dishes
    ) {}

    public static DishByCategoryResponseDTO fromEntity(List<Dish> dishes) {
        Map<Category, List<Dish>> dishesByCategory = dishes.stream()
            .collect(Collectors.groupingBy(dish -> dish.getCategory()));

        List<CategoryWithDishesDTO> categoryDTOs = dishesByCategory.entrySet().stream()
            .map(entry -> new CategoryWithDishesDTO(
                entry.getKey().getName(),
                entry.getValue()
            ))
            .toList();

        return new DishByCategoryResponseDTO(categoryDTOs);
    }
}