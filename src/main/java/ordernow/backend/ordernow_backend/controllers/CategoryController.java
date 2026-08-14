package ordernow.backend.ordernow_backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.requests.dish.CreateDishRequest;
import ordernow.backend.ordernow_backend.responses.category.CategoryResponse;
import ordernow.backend.ordernow_backend.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping("/get")
    public CategoryResponse getCategories() {
        return this.categoryService.getCategories();
    }

    @PostMapping("/create")
    public CategoryResponse createCategory(/*@RequestBody CreateDishRequest createDishRequest*/) {
        return this.categoryService.createCategory();
    }
}
