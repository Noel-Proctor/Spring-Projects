package com.ecommerce.store.repositories;

import com.ecommerce.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Query("select ci FROM CartItem ci where ci.product.productId = ?1 AND ci.cart.cartId = ?2")
    CartItem findByProductIdAndCartId(Long productId, Long cartId);
}
