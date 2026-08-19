package ordernow.backend.ordernow_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ordernow.backend.ordernow_backend.entities.Category;
import ordernow.backend.ordernow_backend.entities.Restaurant;
import ordernow.backend.ordernow_backend.exceptions.DuplicateResourceException;
import ordernow.backend.ordernow_backend.exceptions.ResourceNotFoundException;
import ordernow.backend.ordernow_backend.repositories.CategoryRepository;
import ordernow.backend.ordernow_backend.requests.category.CreateCategoryRequest;
import ordernow.backend.ordernow_backend.requests.category.DeleteCategoryRequest;
import ordernow.backend.ordernow_backend.responses.category.CategoryResponse;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private AuthService authService;

    public CategoryService(CategoryRepository categoryRepository, AuthService authService) {
        this.categoryRepository = categoryRepository;
        this.authService = authService;
    }
    
    public CategoryResponse getOwnCategories() {
        List<Category> categoryList = this.categoryRepository.findByRestaurant(authService.getAuthenticatedUser().getRestaurant());

        if (categoryList == null || categoryList.isEmpty()) {
            throw new ResourceNotFoundException("No se ha encontrado ninguna categoría creada.");
        }
        
        return new CategoryResponse(categoryList, "Se han encontrado las categorías.");
    }

    public CategoryResponse getCategories() {
        List<Category> categoryList = this.categoryRepository.findByRestaurantIsNullMatchesOrRestaurant(authService.getAuthenticatedUser().getRestaurant());

        if (categoryList == null || categoryList.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado las categorías.");
        } 
        
        return new CategoryResponse(categoryList, "Se han encontrado las categorías.");
    }

    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {        
        if(checkIfCategoryAlreadyExists(createCategoryRequest.getName(), authService.getAuthenticatedUser().getRestaurant())) {

            throw new DuplicateResourceException("La categoría ya existe por lo tanto no puede ser creada.");
        }

        Category newCategory = categoryRepository.save(new Category(createCategoryRequest.getName(), authService.getAuthenticatedUser().getRestaurant()));

        List<Category> categoryList = List.of(newCategory);

        return new CategoryResponse(categoryList, "La categoría se ha creado correctamente.");
    }

    public CategoryResponse deleteCategory(DeleteCategoryRequest deleteCategoryRequest) {        
        if(!checkIfCategoryAlreadyExists(deleteCategoryRequest.getName(), authService.getAuthenticatedUser().getRestaurant())) {

            throw new ResourceNotFoundException("No se ha encontrado la categoría que se está intentando borrar.");
        } 

        categoryRepository.delete(categoryRepository.findByName(deleteCategoryRequest.getName()));

        return new CategoryResponse(null, "La categoría se ha borrado correctamente.");
    }

    public boolean checkIfCategoryAlreadyExists(String categoryName, Restaurant restaurant) {
        return categoryRepository.existsByNameAndRestaurant(categoryName, restaurant);
    }
}
