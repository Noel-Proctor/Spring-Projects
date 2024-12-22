package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.exceptions.APIException;
import com.ecommerce.store.exceptions.ResourceNotFoundException;
import com.ecommerce.store.model.Category;
import com.ecommerce.store.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No categories currently exist in the database. Please add a category and try again");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category  savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if (savedCategory != null) {
            throw new APIException("Category with name "+category.getCategoryName()+" already exists");
        }

        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(long categoryId) {
        Optional<Category> categoryToBeDeleted = categoryRepository.findById(categoryId);

        if (categoryToBeDeleted.isPresent()) {
            categoryRepository.delete(categoryToBeDeleted.get());
            return "Category " + categoryId + " successfully deleted";
        }
        throw new APIException(String.format("No category with ID %s exists in database.", categoryId));
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
            throw new ResourceNotFoundException("Category", "Id", categoryIn.getCategoryId());
        }
    }

}

