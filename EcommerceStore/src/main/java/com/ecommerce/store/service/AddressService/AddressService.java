package com.ecommerce.store.service.AddressService;


import com.ecommerce.store.model.User;
import com.ecommerce.store.payload.AddressDTO;
import com.ecommerce.store.payload.AddressResponse;
import com.ecommerce.store.security.response.MessageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface AddressService {
    AddressDTO createUserAddress(AddressDTO addressDTO, User user);

    AddressResponse getUserAddresses(Integer page);

    AddressDTO getSpecificAddress(Long addressId);

    AddressDTO updateAddressById(Long addressId, @Valid AddressDTO addressDTO);

    MessageResponse deleteAddress(@NotNull Long addressId);
}
