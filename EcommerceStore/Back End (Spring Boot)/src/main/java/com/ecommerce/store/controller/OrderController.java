package com.ecommerce.store.controller;

import com.ecommerce.store.payload.OrderDTO;
import com.ecommerce.store.payload.OrderRequestDTO;
import com.ecommerce.store.service.OrderService.OrderService;
import com.ecommerce.store.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {


    @Autowired
    private final AuthUtil authUtil;

    @Autowired
    private OrderService orderService;

    public OrderController(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable String paymentMethod, @RequestBody OrderRequestDTO orderRequestDTO) {

        OrderDTO orderDTO = orderService.placeOrder(paymentMethod, orderRequestDTO);

        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }








}
