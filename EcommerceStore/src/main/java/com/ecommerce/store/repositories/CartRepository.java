package com.ecommerce.store.repositories;

import com.ecommerce.store.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c where c.user = ?1")
    Cart findCartByUserId(Long userId);


}
