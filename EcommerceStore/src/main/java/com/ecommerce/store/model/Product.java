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
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long product_id;

    @NotBlank
    @Size(min = 5, max = 100, message = "Product description name must be between 0-255 characters long")
    private String product_name;

    @NotBlank
    @Size(min = 5, max = 100, message = "Product description name must be between 0-255 characters long")
    private String description;

    private int quantity;
    private double price;
    private double special_Price;
    private String image;


    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

//    private double discount;
















}
