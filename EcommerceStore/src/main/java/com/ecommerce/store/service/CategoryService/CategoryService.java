package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);



}
