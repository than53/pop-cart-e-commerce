package com.than.project.pop_cart_ecom.service.impl;

import com.than.project.pop_cart_ecom.exception.APIException;
import com.than.project.pop_cart_ecom.exception.ResourceNotFoundException;
import com.than.project.pop_cart_ecom.model.*;
import com.than.project.pop_cart_ecom.payload.OrderDTO;
import com.than.project.pop_cart_ecom.payload.OrderItemDTO;
import com.than.project.pop_cart_ecom.repository.*;
import com.than.project.pop_cart_ecom.service.CartService;
import com.than.project.pop_cart_ecom.service.OrderService;
import com.than.project.pop_cart_ecom.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final AddressRepository addressRepository;
    private final AuthUtil authUtil;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderDTO placeOrder(String email, String paymentMethod, Long addressId, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage) {

        Cart cart = cartRepository.findCartByEmail(email);

        if(cart == null){
            throw new ResourceNotFoundException("Cart","email", email);
        }

        MyUser user = authUtil.loggedInUser();

        List<Address> userAddress = user.getAddresses();

        Address addressFromDb = userAddress.stream()
                .filter(add -> add.getAddressId().equals(addressId))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));

        Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted !");
        order.setAddress(addressFromDb);

        Payment payment = new Payment(paymentMethod, pgPaymentId,pgStatus,pgResponseMessage, pgName);
        payment.setOrder(order);
        payment = paymentRepository.save(payment);
        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

       List<CartItem> cartItems = cart.getCartItems();

       if(cartItems.isEmpty()){
           throw new APIException("Cart is Empty");
       }

       List<OrderItem> orderItems = new ArrayList<>();
       for(CartItem cartItem : cartItems){
           OrderItem orderItem = new OrderItem();
           orderItem.setProduct(cartItem.getProduct());
           orderItem.setQuantity(cartItem.getQuantity());
           orderItem.setDiscount(cartItem.getDiscount());
           orderItem.setOrderProductPrice(cartItem.getProductPrice());
           orderItem.setOrder(savedOrder);
           orderItems.add(orderItem);
       }

       orderItems = orderItemRepository.saveAll(orderItems);


       cart.getCartItems().forEach(item -> {
           int quantity = item.getQuantity();
           Product product = item.getProduct();
           product.setQuantity(product.getQuantity() - quantity);
           productRepository.save(product);

           cartService.deleteProductFromCart(cart.getCartId(), item.getProduct().getProductId());
       });

       OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
       orderItems.forEach(item ->
               orderDTO.getOrderItems()
                       .add(modelMapper.map(item, OrderItemDTO.class)));

       orderDTO.setAddressId(addressId);


        return orderDTO;
    }
}
