package com.than.project.pop_cart_ecom.controller;

import com.than.project.pop_cart_ecom.model.Cart;
import com.than.project.pop_cart_ecom.payload.CartDTO;
import com.than.project.pop_cart_ecom.repository.CartRepository;
import com.than.project.pop_cart_ecom.service.CartService;
import com.than.project.pop_cart_ecom.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {


    private final AuthUtil authUtil;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable("productId") Long productId,
                                                    @PathVariable("quantity") Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);

        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts(){

        List<CartDTO> cartDTOS = cartService.getAllCarts();

        return new ResponseEntity<>(cartDTOS, HttpStatus.FOUND);

    }

    @GetMapping("/carts/user/cart")
    public ResponseEntity<CartDTO> getCartById(){
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        long cartId = cart.getCartId();
        CartDTO cartDTOS = cartService.getCarts(emailId, cartId);

        return new ResponseEntity<>(cartDTOS, HttpStatus.OK);
    }

    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable("productId") Long productId,
                                              @PathVariable("operation") String operation){

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete")? -1 :1);

        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
}
