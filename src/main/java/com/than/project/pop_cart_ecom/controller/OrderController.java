package com.than.project.pop_cart_ecom.controller;

import com.than.project.pop_cart_ecom.payload.OrderDTO;
import com.than.project.pop_cart_ecom.payload.OrderRequestDTO;
import com.than.project.pop_cart_ecom.service.OrderService;
import com.than.project.pop_cart_ecom.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AuthUtil authUtil;

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(
            @PathVariable("paymentMethod") String paymentMethod,
            @RequestBody OrderRequestDTO orderRequestDTO){

        String email = authUtil.loggedInEmail();

        OrderDTO placeOrder = orderService.placeOrder(
                email,
                paymentMethod,
                orderRequestDTO.getAddressId(),
                orderRequestDTO.getPgName(),
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgResponseMessage()
        );

        return new ResponseEntity<>(placeOrder, HttpStatus.CREATED);
    }
}
