package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.payload.ProductDTO;
import com.ecommerce.store.payload.ProductResponse;

public interface ProductService {

    ProductResponse getProducts(Integer page, Integer size, String sortBy, String orderBy);

    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);
    ProductDTO updateProduct(ProductDTO productDTO);
    ProductDTO deleteProduct(Long productId);
}
