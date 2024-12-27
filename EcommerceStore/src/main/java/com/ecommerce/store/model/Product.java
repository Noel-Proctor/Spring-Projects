package com.ecommerce.store.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 5, max = 100, message = "Product description name must be between 0-255 characters long")
    private String productName;

    @NotBlank
    @Size(min = 5, max = 100, message = "Product description name must be between 0-255 characters long")
    private String description;

    private int quantity;
    private double price;
    private double special_Price;
    private String image;
    private double discount;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    public void setSpecial_Price() {
        this.special_Price = price - (price *(discount/100));
    }
}
