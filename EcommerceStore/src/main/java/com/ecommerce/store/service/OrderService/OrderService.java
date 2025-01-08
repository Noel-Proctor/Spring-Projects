package com.ecommerce.store.service.OrderService;

import com.ecommerce.store.payload.OrderDTO;
import com.ecommerce.store.payload.OrderRequestDTO;

public interface OrderService {
    OrderDTO placeOrder(String paymentMethod, OrderRequestDTO orderRequestDTO);
}
