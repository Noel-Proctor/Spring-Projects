package com.ecommerce.store.repositories;

import com.ecommerce.store.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c where c.user.userId = ?1")
    Optional<Cart> findCartByUser(Long userId);

    @Query("SELECT c FROM Cart c where c.user.email = ?1")
    Optional<Cart> findCartByEmail(String userEmail);
}
