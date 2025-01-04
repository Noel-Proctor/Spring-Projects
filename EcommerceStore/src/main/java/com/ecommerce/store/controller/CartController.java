package com.ecommerce.store.controller;

import com.ecommerce.store.payload.CartDTO;
import com.ecommerce.store.service.CartService.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity) {

        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);

    }

    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts() {

        List<CartDTO> carts = cartService.getAllCarts();
        return new ResponseEntity<>(carts, HttpStatus.FOUND);
    }

    @GetMapping("/carts/user/cart")
    public ResponseEntity<CartDTO> getUserCart() {

        CartDTO userCart = cartService.getUserCart();

        return new ResponseEntity<>(userCart, HttpStatus.FOUND);
    }

    @PostMapping("/carts/products/{productId}/update/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId, @PathVariable String operation) {

        CartDTO cartDTO = cartService.updateProductQuantity(productId,
                operation.equalsIgnoreCase("delete")? -1:1);

        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long productId, @PathVariable Long cartId) {

        String status = cartService.deleteProductFromCart( productId, cartId);

        return new ResponseEntity<>(status, HttpStatus.OK);

    }



}


