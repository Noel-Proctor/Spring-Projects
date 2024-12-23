package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.exceptions.APIException;
import com.ecommerce.store.exceptions.ResourceNotFoundException;
import com.ecommerce.store.model.Category;
import com.ecommerce.store.payload.CategoryDTO;
import com.ecommerce.store.payload.CategoryResponse;
import com.ecommerce.store.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new APIException("No categories currently exist in the database. Please add a category and try again");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category  savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if (savedCategory != null) {
            throw new APIException("Category with name "+category.getCategoryName()+" already exists");
        }
        return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Optional<Category> categoryToBeDeleted = categoryRepository.findById(categoryId);

        if (categoryToBeDeleted.isPresent()) {
            categoryRepository.delete(categoryToBeDeleted.get());
            return modelMapper.map( categoryToBeDeleted.get(), CategoryDTO.class);
        }
        throw new APIException(String.format("No category with ID %s exists in database.", categoryId));
    }


    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO
    ) {
        Optional<Category> optionalCategory= categoryRepository.findById(categoryDTO.getCategoryId());

        if(optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(categoryDTO.getCategoryName());
            categoryRepository.save(existingCategory);
            return modelMapper.map(existingCategory, CategoryDTO.class);

        }else {
            throw new ResourceNotFoundException("Category", "Id", categoryDTO.getCategoryId());
        }
    }

}

