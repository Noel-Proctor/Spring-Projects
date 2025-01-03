package com.ecommerce.store.payload;

import com.ecommerce.store.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long cartId;
    private User user;
    private List<ProductDTO> products = new ArrayList<>();
    private Double totalPrice = 0.0;
}