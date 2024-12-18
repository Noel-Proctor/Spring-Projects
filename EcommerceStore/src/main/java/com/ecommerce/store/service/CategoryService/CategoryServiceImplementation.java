package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


        Category category = categories.stream().filter(e -> e.getCategoryId()
                        == categoryId).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));

        if (category != null) {
            categories.remove(category);
            return "Category " + categoryId + " successfully deleted";
        }

        return "Category " + categoryId + " does not exist.";
    }

    @Override
    public String updateCategory(Category categoryIn) {
        Optional<Category> optionalCategory = categories.stream().filter(e-> e.getCategoryId() == categoryIn.getCategoryId()).
                findFirst();

        if(optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(categoryIn.getCategoryName());
            return "Category " + categoryIn.getCategoryId() + " successfully update";

        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category does not exist.");
        }

    }

}

