package ordernow.backend.ordernow_backend.controllers.publ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ordernow.backend.ordernow_backend.dtos.DishByCategoryResponseDTO;
import ordernow.backend.ordernow_backend.services.DishService;

@RestController
@RequestMapping("/api/public/dish")
public class DishPublicController {
    private DishService dishService;

    public DishPublicController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{qrToken}")
    public DishByCategoryResponseDTO getDishesByCategory(@PathVariable(name = "qrToken") String qrToken) {
        return this.dishService.getDishesByServiceTable(qrToken);
    }
}
