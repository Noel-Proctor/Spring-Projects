package com.ecommerce.store.repositories;

import com.ecommerce.store.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c where c.user.userId = ?1")
    Optional<Cart> findCartByUser(Long userId);

    @Query("SELECT c FROM Cart c where c.user.email = ?1")
    Optional<Cart> findCartByEmail(String userEmail);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.productId = ?1")
    List<Cart> findCartsByProductId(long productId);


}
