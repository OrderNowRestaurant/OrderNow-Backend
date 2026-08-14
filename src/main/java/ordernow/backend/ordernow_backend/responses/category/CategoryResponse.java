package ordernow.backend.ordernow_backend.responses.category;

import java.util.List;

import ordernow.backend.ordernow_backend.entities.Category;
import ordernow.backend.ordernow_backend.responses.BaseResponse;

public class CategoryResponse extends BaseResponse {
    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryResponse(List<Category> categoryList, String message) {
        super(message);
        this.categoryList = categoryList;
    }
}
