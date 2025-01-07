package com.ecommerce.store.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String addressId;
    private String street;
    private String buildingName;
    private String city;
    private String county;
    private String postcode;


}
