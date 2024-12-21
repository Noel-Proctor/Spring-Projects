package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.model.Category;
import com.ecommerce.store.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(long categoryId) {
        Optional<Category> categoryToBeDeleted = categoryRepository.findById(categoryId);

        if (categoryToBeDeleted.isPresent()) {
            categoryRepository.delete(categoryToBeDeleted.get());
            return "Category " + categoryId + " successfully deleted";
        }
        return "Category " + categoryId + " does not exist.";
    }


    @Override
    public String updateCategory(Category categoryIn) {
        Optional<Category> optionalCategory= categoryRepository.findById(categoryIn.getCategoryId());

        if(optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(categoryIn.getCategoryName());
            categoryRepository.save(existingCategory);
            return "Category " + categoryIn.getCategoryId() + " successfully update";

        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category does not exist.");
        }
    }

}

