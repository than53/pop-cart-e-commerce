package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.payload.CartDTO;

import java.util.List;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCarts(String emailId, long cartId);
}
