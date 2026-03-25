package com.than.project.pop_cart_ecom.repository;

import com.than.project.pop_cart_ecom.model.MyUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MyUserRepository extends JpaRepository<MyUser, Long> {
   Optional<MyUser> findByUsername(String username);

   Boolean existsByUsername(String username);

   Boolean existsByEmail(String email);

    boolean existsByUserName(String user1);
}
