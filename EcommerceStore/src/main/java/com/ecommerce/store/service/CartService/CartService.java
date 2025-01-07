package com.ecommerce.store.service.CartService;


import com.ecommerce.store.payload.CartDTO;

import java.util.List;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getUserCart();

    CartDTO updateProductQuantity(Long productId, int operation);

    String deleteProductFromCart(Long productId, Long cartId);

    void updateProductsInCart(Long cartId, Long productId);
}
