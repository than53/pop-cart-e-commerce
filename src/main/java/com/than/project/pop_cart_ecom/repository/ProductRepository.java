package com.than.project.pop_cart_ecom.repository;

import com.than.project.pop_cart_ecom.model.Category;
import com.than.project.pop_cart_ecom.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageable);

    Page<Product> findByProductNameLikeIgnoreCase(String productName, Pageable pageable);

    Optional<Product> findByProductName(String productName);
}
