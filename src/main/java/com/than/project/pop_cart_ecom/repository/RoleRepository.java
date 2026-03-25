package com.than.project.pop_cart_ecom.repository;

import com.than.project.pop_cart_ecom.model.AppRole;
import com.than.project.pop_cart_ecom.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
