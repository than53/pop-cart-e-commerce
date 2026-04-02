package com.than.project.pop_cart_ecom.repository;

import com.than.project.pop_cart_ecom.model.Cart;
import com.than.project.pop_cart_ecom.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE  c.product.productId = ?1 AND c.cart.cartId = ?2")
    CartItem findCartItemByProductIdAndCartId(Long productId, Long cartId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.product.productId = ?1 AND c.cart.cartId = ?2")
    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.product.productId = :productId")
    void deleteByProductId(Long productId);
}
