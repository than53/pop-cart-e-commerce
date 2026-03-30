package com.than.project.pop_cart_ecom.service.impl;

import com.than.project.pop_cart_ecom.exception.APIException;
import com.than.project.pop_cart_ecom.exception.ResourceNotFoundException;
import com.than.project.pop_cart_ecom.model.Cart;
import com.than.project.pop_cart_ecom.model.CartItem;
import com.than.project.pop_cart_ecom.model.Product;
import com.than.project.pop_cart_ecom.payload.CartDTO;
import com.than.project.pop_cart_ecom.payload.ProductDTO;
import com.than.project.pop_cart_ecom.repository.CartItemRepository;
import com.than.project.pop_cart_ecom.repository.CartRepository;
import com.than.project.pop_cart_ecom.repository.ProductRepository;
import com.than.project.pop_cart_ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AuthUtil authUtil;
    private final ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(
                 product.getProductId() , cart.getCartId()
        );

        if(cartItem != null){
            throw new APIException("Product " + product.getProductName() + "Already exists in the cart");
        }

        if(product.getQuantity() == 0){
            throw new APIException(product.getProductName() + "is not available");
        }

        if(product.getQuantity() < quantity){
            throw new APIException("Please, make an order of the " + product.getProductName()
            + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity() - quantity);
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productDTOStream = cartItems
                .stream().map(item -> {
                   ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
                   map.setQuantity(item.getQuantity());
                   return map;
                });

        cartDTO.setProducts(productDTOStream.toList());

        return cartDTO;
    }

    private Cart createCart(){
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart !=nulll){
            return userCart;
        }
        Cart cart = new Cart();

        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        return cartRepository.save(cart);
    }
}
