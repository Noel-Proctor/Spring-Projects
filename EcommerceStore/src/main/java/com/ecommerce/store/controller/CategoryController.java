package com.ecommerce.store.controller;


import com.ecommerce.store.model.Category;
import com.ecommerce.store.service.CategoryService.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping("/api/public/categories")
    @RequestMapping(path = "public/categories", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

//    @PostMapping("/api/public/categories")
    @RequestMapping(path = "public/categories", method = RequestMethod.POST)
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok("Category added successfully");
    }

//    @DeleteMapping("api/admin/categories/{categoryId}")
    @RequestMapping(path = "admin/categories/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
    }

//    @PutMapping("/api/public/categories")
    @RequestMapping(path="public/categories", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category) {
            String status = categoryService.updateCategory(category);
            return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
