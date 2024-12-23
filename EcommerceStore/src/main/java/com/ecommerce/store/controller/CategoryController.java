package com.ecommerce.store.controller;


import com.ecommerce.store.payload.CategoryDTO;
import com.ecommerce.store.payload.CategoryResponse;
import com.ecommerce.store.service.CategoryService.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping("/api/public/categories")
    @RequestMapping(path = "public/categories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "page") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer size) {
        return ResponseEntity.ok(categoryService.getAllCategories(pageNumber, size));
    }

//    @PostMapping("/api/public/categories")
    @RequestMapping(path = "public/categories", method = RequestMethod.POST)
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

//    @DeleteMapping("api/admin/categories/{categoryId}")
    @RequestMapping(path = "admin/categories/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
            CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }

//    @PutMapping("/api/public/categories")
    @RequestMapping(path="public/categories", method = RequestMethod.PUT)
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO);
            return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
    }

}
