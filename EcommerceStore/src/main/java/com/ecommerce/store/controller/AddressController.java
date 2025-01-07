package com.ecommerce.store.controller;

import com.ecommerce.store.config.AppConstants;
import com.ecommerce.store.model.User;
import com.ecommerce.store.payload.AddressDTO;
import com.ecommerce.store.payload.AddressResponse;
import com.ecommerce.store.service.AddressService.AddressService;
import com.ecommerce.store.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createUserAddress(@Valid @RequestBody AddressDTO addressDTO) {

        User user = authUtil.loggedInUser();
        AddressDTO createdAddress = addressService.createUserAddress(addressDTO, user);

        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @GetMapping("/address")
    public ResponseEntity<AddressResponse> getUserAddresses(
            @RequestParam(name = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer page){
        AddressResponse response = addressService.getUserAddresses(page);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }


    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> getUserAddresses(@PathVariable Long addressId){
        AddressDTO addressDto = addressService.getSpecificAddress(addressId);
        return new ResponseEntity<>(addressDto, HttpStatus.FOUND);
    }






}
