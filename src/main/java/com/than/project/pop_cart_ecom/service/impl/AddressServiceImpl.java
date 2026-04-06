package com.than.project.pop_cart_ecom.service.impl;

import com.than.project.pop_cart_ecom.model.Address;
import com.than.project.pop_cart_ecom.model.MyUser;
import com.than.project.pop_cart_ecom.payload.AddressDTO;
import com.than.project.pop_cart_ecom.repository.AddressRepository;
import com.than.project.pop_cart_ecom.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO, MyUser user) {
        Address address = modelMapper.map(addressDTO, Address.class);

        List<Address> userAddress = user.getAddresses();
        userAddress.add(address);
        user.setAddresses(userAddress);
        address.setUser(user);

        Address saveAddress = addressRepository.save(address);

        return modelMapper.map(saveAddress, AddressDTO.class);
    }
}
