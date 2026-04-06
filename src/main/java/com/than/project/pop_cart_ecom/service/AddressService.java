package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.model.MyUser;
import com.than.project.pop_cart_ecom.payload.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO saveAddress(AddressDTO addressDTO, MyUser user);

    List<AddressDTO> getAllAddress();

    List<AddressDTO> getUserAddress(MyUser user);

    AddressDTO getAddressById(Long addressId);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO, MyUser user);

    String deleteAddress(Long addressId, MyUser user);
}
