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
@Table(name = "Addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, max = 100, message = "Street name must be between 5-100 characters long")
    private String street;

    @NotBlank
    @Size(min = 5, max = 100, message = "Building name must be between 5-100 characters long")
    private String buildingName;

    @NotBlank
    @Size(min = 2, max = 100, message = "City name must be between 2-100 characters long")
    private String city;

    @NotBlank @Size(min = 2, max = 50, message = "County name must be between 2-50 characters long")
    private String county;

    @NotBlank @Size(min = 6, max = 8, message = "Postcode must be between 6-8 characters long")
    private String postcode;


    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;


    public Address(String street, String buildingName, String city, String county, String postcode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.county = county;
        this.postcode = postcode;
    }
}
