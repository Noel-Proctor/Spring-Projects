package com.ecommerce.store.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long orderItemId;
    private double discount;
    private double orderedProductPrice;
    private int quantity;
    private ProductDTO product;
}
