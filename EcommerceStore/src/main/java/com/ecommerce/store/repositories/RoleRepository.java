package com.ecommerce.store.repositories;

import com.ecommerce.store.model.ApplicationRole;
import com.ecommerce.store.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {


    Optional<Role> findByRoleName(ApplicationRole role);

}
