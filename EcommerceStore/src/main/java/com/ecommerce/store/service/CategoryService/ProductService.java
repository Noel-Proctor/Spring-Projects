package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.payload.ProductDTO;
import com.ecommerce.store.payload.ProductResponse;

public interface ProductService {

    ProductResponse getProducts(Integer page, Integer size, String sortBy, String orderBy);

    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);
    ProductDTO updateProduct(ProductDTO productDTO, long productId);
    ProductDTO deleteProduct(Long productId);
    ProductResponse getProductsByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String direction, String field);
    ProductResponse getAllProducts();
    ProductResponse getProductsByKeyword(String Keyword);
}
