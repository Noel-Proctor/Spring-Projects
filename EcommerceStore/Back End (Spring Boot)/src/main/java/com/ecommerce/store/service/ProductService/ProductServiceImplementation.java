package com.ecommerce.store.service.ProductService;

import com.ecommerce.store.exceptions.APIException;
import com.ecommerce.store.exceptions.ResourceNotFoundException;
import com.ecommerce.store.model.Cart;
import com.ecommerce.store.model.Category;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.payload.CartDTO;
import com.ecommerce.store.payload.ProductDTO;
import com.ecommerce.store.payload.ProductResponse;
import com.ecommerce.store.repositories.CartRepository;
import com.ecommerce.store.repositories.CategoryRepository;
import com.ecommerce.store.repositories.ProductRepository;
import com.ecommerce.store.service.CartService.CartService;
import com.ecommerce.store.service.FileService.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImplementation implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;


    @Value("${project.image}")
    private String path;

    @Value("${image.base.url}")
    String imageBaseUrl;



    //-------------------------------------------------------------
    //    Add  and update Products
    //-------------------------------------------------------------
    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", productDTO.getCategoryId())
                );
        List<Product> productList = category.getProducts();

        boolean productAlreadyExists = productList.stream().anyMatch(
                product -> product.getProductName().equalsIgnoreCase(productDTO.getProductName())
        );

        if(productAlreadyExists){
            throw new APIException("Product already exists");
        }

        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        product.setSpecialPrice();
        Product savedProduct = productRepository.save(product);
        category.getProducts().add(product);
        categoryRepository.save(category);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }


    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, long productId) {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productId)
        );

        product.setImage(productDTO.getImage());
        product.setProductName(productDTO.getProductName());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setSpecialPrice();

        Product savedProduct = productRepository.save(product);

        List<Cart> carts = cartRepository.findCartsByProductId(productId);

        List<CartDTO> cartDTOs = carts.stream().map(
                cart->{
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

                    List<ProductDTO> productDTOS = cart.getCartItems().stream().map(
                            item ->{
                                ProductDTO p = modelMapper.map(item.getProduct(),
                                        ProductDTO.class);
                                return p;
                            }
                    ).toList();

                    cartDTO.setProducts(productDTOS);
                    return cartDTO;
                }
        ).toList();

        cartDTOs.forEach(cart-> cartService.updateProductsInCart(cart.getCartId(), productId));



        return modelMapper.map(savedProduct, ProductDTO.class);
    }
    //-------------------------------------------------------------
    //    Delete Products
    //-------------------------------------------------------------
    @Override
    public Map<String,ProductDTO> deleteProduct(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productId)
        );

        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        carts.forEach(cart-> cartService.deleteProductFromCart(cart.getCartId(), productId));

        productRepository.delete(product);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        Map<String, ProductDTO> response = new HashMap<>();
        response.put(String.format("Product with Id %s has been deleted", productId), productDTO );




        return response;
    }
    //-------------------------------------------------------------
    //    GET Products
    //-------------------------------------------------------------
    @Override
    public ProductResponse getProductsByCategoryId(Long categoryId,Integer pageNumber, Integer pageSize, String direction, String field) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", categoryId)
                );

        Sort sortAndOrderBy = direction.equalsIgnoreCase("asc") ? Sort.by(field).ascending() : Sort.by(field).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sortAndOrderBy);
        Page<Product> productPage= productRepository.findByCategoryOrderByPriceAsc(category, pageable);
        List<Product> products = productPage.getContent();

        if (products.isEmpty()) {
            throw new APIException(String.format("No products match the Category: %s", category));
        }

        return getProductResponse(productPage, products);

    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String direction, String orderBy) {

        Sort sortAndOrderBy = direction.equalsIgnoreCase("asc") ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sortAndOrderBy);
        Page<Product> productPage= productRepository.findAll(pageable);
        List<Product> products =productPage.getContent();

        if (products.isEmpty()) {
            throw new APIException("No products currently exist in the database");
        }
        return getProductResponse(productPage, products);
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String direction, String orderBy) {

        Sort sortAndOrderBy = direction.equalsIgnoreCase("asc") ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sortAndOrderBy);
        Page<Product> productPage= productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%", pageable);
        List<Product> products = productPage.getContent();

        if (products.isEmpty()) {
            throw new APIException(String.format("No products match the keyword: %s", keyword));
        }
        return getProductResponse(productPage, products);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //Get product from DB
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ResourceNotFoundException("Product", "id", productId)
        );

        //Upload image

        String fileName = fileService.uploadImage(path, image);
        //Save image path to product (filename)
        product.setImage(fileName);
        Product updatedProduct =productRepository.save(product);

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }


    private ProductResponse getProductResponse(Page<Product> productPage, List<Product> products) {
        List<ProductDTO> productDTOS = products.stream().map(product -> {
            product.setImage(constructImageURL(product.getImage()));
            return modelMapper.map(product, ProductDTO.class);
        }).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setLastPage(productPage.isLast());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getNumberOfElements());
        return productResponse;
    }


    private String constructImageURL(String imageName){
        return imageBaseUrl.endsWith("/") ? imageBaseUrl + imageName : imageBaseUrl+ "/" + imageName;


    }

}

