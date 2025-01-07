package com.ecommerce.store.service.AddressService;

import com.ecommerce.store.exceptions.APIException;
import com.ecommerce.store.model.Address;
import com.ecommerce.store.model.User;
import com.ecommerce.store.payload.AddressDTO;
import com.ecommerce.store.payload.AddressResponse;
import com.ecommerce.store.repositories.AddressRepository;
import com.ecommerce.store.repositories.UserRepository;
import com.ecommerce.store.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private UserRepository userRepository;


    @Override
    public AddressDTO createUserAddress(AddressDTO addressDTO, User user) {

        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);
        List<Address> addressesList = user.getAddresses();
        addressesList.add(address);
        user.setAddresses(addressesList);
        Address savedAddress = addressRepository.save(address);
        AddressDTO savedAddressDTO = modelMapper.map(savedAddress, AddressDTO.class);

        return savedAddressDTO;
    }

    @Override
    public AddressResponse getUserAddresses(Integer page) {

        Sort sortAndOrder = Sort.by("street").ascending();
        User user = authUtil.loggedInUser();

        Pageable pageable = PageRequest.of(page, 5, sortAndOrder);
        Page<Address> addressPage = addressRepository.findByUser(user, pageable);

        List<Address> addresses = addressPage.getContent();

        if (addresses.isEmpty()){
            throw new APIException("No addresses found for user");
        }

        List<AddressDTO> addressDTOList = addresses.stream().map(
                address -> modelMapper.map(address, AddressDTO.class)).toList();

        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setAddresses(addressDTOList);
        addressResponse.setPageNumber(addressPage.getNumber());
        addressResponse.setPageSize(addressPage.getSize());
        addressResponse.setTotalPages(addressPage.getTotalPages());
        addressResponse.setTotalElements(addressPage.getNumberOfElements());
        addressResponse.setLastPage(addressPage.isLast());

        return addressResponse;

    }

    @Override
    public AddressDTO getSpecificAddress(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(
                ()-> new APIException("No address found"));
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        return addressDTO;

    }
}
