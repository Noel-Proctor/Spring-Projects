package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private List<Category> categories = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);

    }

    @Override
    public String deleteCategory(long categoryId) {

        try{
            Category category = categories.stream().filter(e->e.getCategoryId()
                    == categoryId).findFirst().orElse(null);

            if (category != null) {
                categories.remove(category);
                return "Category "+categoryId+" successfully deleted";
            }

            return "Category "+categoryId+" does not exist.";

        }catch (Exception e){
            return "Error deleting category "+categoryId;
        }

    }
}
