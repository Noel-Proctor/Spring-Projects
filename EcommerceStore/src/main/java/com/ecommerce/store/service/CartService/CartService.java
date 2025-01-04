package com.ecommerce.store.service.CartService;


import com.ecommerce.store.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

}
