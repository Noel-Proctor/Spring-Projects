package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.payload.CategoryDTO;
import com.ecommerce.store.payload.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer page, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO);
}
