package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.model.MyUser;
import com.than.project.pop_cart_ecom.payload.AddressDTO;

public interface AddressService {

    AddressDTO saveAddress(AddressDTO addressDTO, MyUser user);
}
