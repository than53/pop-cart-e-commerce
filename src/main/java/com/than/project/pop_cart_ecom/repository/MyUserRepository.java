package com.than.project.pop_cart_ecom.repository;

import com.than.project.pop_cart_ecom.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MyUserRepository extends JpaRepository<MyUser, Long> {
   Optional<MyUser> findByUsername(String username);
}
