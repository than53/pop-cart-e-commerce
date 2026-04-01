package com.than.project.pop_cart_ecom.controller;

import com.than.project.pop_cart_ecom.payload.CartDTO;
import com.than.project.pop_cart_ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable("productId") Long productId,
                                                    @PathVariable("quantity") Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);

        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }
}
