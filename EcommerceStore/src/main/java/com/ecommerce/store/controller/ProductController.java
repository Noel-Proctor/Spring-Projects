package com.ecommerce.store.controller;


import com.ecommerce.store.payload.ProductDTO;
import com.ecommerce.store.service.CategoryService.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId){

         ProductDTO productAdded =productService.addProduct(productDTO, categoryId);

        return  ResponseEntity.ok(productAdded);



    }
}
