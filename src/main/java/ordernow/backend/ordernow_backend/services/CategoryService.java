package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.Category;
import ordernow.backend.ordernow_backend.entities.User;
import ordernow.backend.ordernow_backend.repositories.CategoryRepository;
import ordernow.backend.ordernow_backend.repositories.UserRepository;
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
    public CategoryResponse createCategory() {
        return new CategoryResponse(null, null);
    }
}
