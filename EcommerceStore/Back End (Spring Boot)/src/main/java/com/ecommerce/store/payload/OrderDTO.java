package com.ecommerce.store.payload;

import com.ecommerce.store.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private String email;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private Double total_Amount;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
    private PaymentDTO payment;
    private AddressDTO address;


}
