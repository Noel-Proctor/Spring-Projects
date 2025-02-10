package com.ecommerce.store.controller;


import com.ecommerce.store.config.AppConstants;
import com.ecommerce.store.payload.ProductDTO;
import com.ecommerce.store.payload.ProductResponse;
import com.ecommerce.store.service.ProductService.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("admin/categories/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO){

         ProductDTO productAdded =productService.addProduct(productDTO);

        return new ResponseEntity<>(productAdded,HttpStatus.CREATED);
    }

    @PostMapping("admin/categories/products")
    public ResponseEntity<List<ProductDTO>> addMultipleProduct(@Valid @RequestBody List<ProductDTO> productDTOList){

        List<ProductDTO> addedProducts = new ArrayList<>();
        for (ProductDTO productDTO : productDTOList) {
            ProductDTO productAdded =productService.addProduct(productDTO);
            addedProducts.add(productAdded);
        }

        return new ResponseEntity<>(addedProducts,HttpStatus.CREATED);
    }


    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "direction", defaultValue = AppConstants.DIRECTION, required = false) String direction,
            @RequestParam(name = "orderBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String orderBy) {

        ProductResponse productResponse = productService.getProductsByCategoryId(categoryId,pageNumber,pageSize,direction,orderBy);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }

    @GetMapping("public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "direction", defaultValue = AppConstants.DIRECTION, required = false) String direction,
            @RequestParam(name = "orderBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String orderBy,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category){
        ProductResponse productResponse = productService.getAllProducts(pageNumber,pageSize,direction,orderBy,keyword,category);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }

    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "direction", defaultValue = AppConstants.DIRECTION, required = false) String direction,
            @RequestParam(name = "orderBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String orderBy) {

        ProductResponse productResponse = productService.getProductsByKeyword(keyword,pageNumber,pageSize,direction,orderBy);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId) {
        ProductDTO product = productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestBody MultipartFile image) throws IOException {

        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

    }

    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<Map<String,ProductDTO>> deleteProduct(@PathVariable Long productId) {
        Map<String,ProductDTO> deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }



}
