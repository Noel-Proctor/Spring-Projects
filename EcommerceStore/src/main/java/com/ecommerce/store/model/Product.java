package com.ecommerce.store.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private double special_Price;
    private String image;

    @DecimalMax(value = "80.00", message = "Discount cannot be greater than 80%. " +
            "Please report to your manager for an immediate disciplinary hearing.")
    private double discount;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    public void setSpecial_Price() {
        this.special_Price = price - (price *(discount/100));
    }
}
