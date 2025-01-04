package com.ecommerce.store.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Size(min = 5, max = 100, message = "Product description name must be between 5-255 characters long")
    private String productName;

    @NotBlank
    @Size(min = 5, max = 100, message = "Product description name must be between 5-255 characters long")
    private String description;

    @Min(value =0, message ="Please enter a quantity greater than or equal to 0.")
    private int quantity;

    @DecimalMin(value = "0.1", message = "Please enter a price greater than 0.")
    private double price;
    private double specialPrice;
    private String image;

    @DecimalMax(value = "80.00", message = "Discount cannot be greater than 80%. " +
            "Please report to your manager for an immediate disciplinary hearing.")
    private double discount;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;

    @ManyToOne
    @JoinColumn(name ="seller_id")
    private User seller;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<CartItem> products = new ArrayList<>();

    public void setSpecialPrice() {
        this.specialPrice = price - (price *(discount/100));
    }

    public Product(String productName, String description, int quantity, double price, String image,
                   double discount, Category category, User seller) {
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.discount = discount;
        this.category = category;
        this.seller = seller;

        if (discount>0){
            setSpecialPrice();
        }else if (discount<=0){
            this.specialPrice = this.price;
        }
    }
}
