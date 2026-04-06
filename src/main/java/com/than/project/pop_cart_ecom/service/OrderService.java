package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.payload.OrderDTO;

public interface OrderService {
    OrderDTO placeOrder(String email, String paymentMethod, Long addressId, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
