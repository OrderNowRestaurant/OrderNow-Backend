package ordernow.backend.ordernow_backend.controllers.publ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.responses.category.CategoryResponse;
import ordernow.backend.ordernow_backend.services.CategoryService;

@RestController
@RequestMapping("/api/public/category")
public class CategoryPublicController {
    private CategoryService categoryService;

    public CategoryPublicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{qrToken}")
    public CategoryResponse getCategories(@PathVariable(name = "qrToken") String qrToken) {
        return this.categoryService.getCategories(qrToken);
    }
}
