package com.than.project.pop_cart_ecom.repository;

import com.than.project.pop_cart_ecom.model.Cart;
import com.than.project.pop_cart_ecom.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE  c.product.product_id = ?1 AND c.cart.cart_id = ?2")
    CartItem findCartItemByProductIdAndCartId(Long productId, Long cartId);
}
