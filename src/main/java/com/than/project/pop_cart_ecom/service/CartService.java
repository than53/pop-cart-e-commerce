package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);
}
