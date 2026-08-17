package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.Category;
import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.repositories.CategoryRepository;
import ordernow.backend.ordernow_backend.repositories.UserRepository;
import ordernow.backend.ordernow_backend.requests.category.CreateCategoryRequest;
import ordernow.backend.ordernow_backend.responses.category.CategoryResponse;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private AuthService authService;
    private UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, AuthService authService, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.authService = authService;
        this.userRepository = userRepository;
    }
    
    public CategoryResponse getCategories() {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();

        List<Category> categoryList = this.categoryRepository.findByRestaurantIsNullMatchesOrRestaurant(user.getRestaurant());

        if (categoryList.isEmpty()) {
            return new CategoryResponse(null, "No se han encontrado las categorías.");
        } else {
            return new CategoryResponse(categoryList, "Se han encontrado las categorías.");
        }
    }

    /**
     * TO DO
     * @return
     */
    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        User user = userRepository.findByUsername(
                authService.getUsername()
        ).get();
        
        if(!checkIfCategoryAlreadyExists(createCategoryRequest.getName(), user.getRestaurant())) {
            Category newCategory = categoryRepository.save(new Category(createCategoryRequest.getName(), user.getRestaurant()));

            List<Category> categoryList = List.of(newCategory);

            return new CategoryResponse(categoryList, "La categoría se ha creado correctamente.");
        } else {
            return new CategoryResponse(null, "La categoría ya existe por lo tanto no puede ser creada.");
        }   
    }

    public boolean checkIfCategoryAlreadyExists(String categoryName, Restaurant restaurant) {
        return categoryRepository.existsByNameAndRestaurant(categoryName, restaurant);
    }
}
