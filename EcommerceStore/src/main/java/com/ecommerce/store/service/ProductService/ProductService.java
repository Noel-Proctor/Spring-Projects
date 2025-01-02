package com.ecommerce.store.service.ProductService;

import com.ecommerce.store.payload.ProductDTO;
import com.ecommerce.store.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ProductService {


    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);
    ProductDTO updateProduct(ProductDTO productDTO, long productId);
    Map<String,ProductDTO> deleteProduct(Long productId);
    ProductResponse getProductsByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String direction, String field);
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String direction, String orderBy);
    ProductResponse getProductsByKeyword(String Keyword, Integer pageNumber, Integer pageSize, String direction, String orderBy);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
