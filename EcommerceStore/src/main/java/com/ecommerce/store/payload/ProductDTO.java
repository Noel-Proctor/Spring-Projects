package com.ecommerce.store.payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private long productId;
    private String product_name;
    private String description;
    private String image;
    private int quantity;
    private double price;
    private double discount;
    private double special_Price;
    private String categoryName;
}
