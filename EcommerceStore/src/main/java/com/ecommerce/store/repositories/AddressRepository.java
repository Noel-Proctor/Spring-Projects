package com.ecommerce.store.repositories;

import com.ecommerce.store.model.Address;
import com.ecommerce.store.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findByUser(User user, Pageable pageable);
}
