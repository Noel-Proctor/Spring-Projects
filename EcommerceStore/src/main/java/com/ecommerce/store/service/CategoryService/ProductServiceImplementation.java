package com.ecommerce.store.service.CategoryService;

import com.ecommerce.store.exceptions.ResourceNotFoundException;
import com.ecommerce.store.model.Category;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.payload.ProductDTO;
import com.ecommerce.store.payload.ProductResponse;
import com.ecommerce.store.repositories.CategoryRepository;
import com.ecommerce.store.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImplementation implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductResponse getProducts(Integer page, Integer size, String sortBy, String orderBy) {
        return null;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", categoryId)
                );


        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        product.setSpecial_Price(
                product.getPrice() - (product.getPrice()*(productDTO.getDiscount()/100))
        );
        return modelMapper.map(productRepository.save(product), ProductDTO.class);



    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        return null;
    }
}
