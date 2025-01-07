package com.ecommerce.store.service.AddressService;


import com.ecommerce.store.model.User;
import com.ecommerce.store.payload.AddressDTO;
import com.ecommerce.store.payload.AddressResponse;

public interface AddressService {
    AddressDTO createUserAddress(AddressDTO addressDTO, User user);

    AddressResponse getUserAddresses(Integer page);

    AddressDTO getSpecificAddress(Long addressId);
}
