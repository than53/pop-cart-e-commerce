package com.than.project.pop_cart_ecom.controller;

import com.than.project.pop_cart_ecom.model.MyUser;
import com.than.project.pop_cart_ecom.payload.AddressDTO;
import com.than.project.pop_cart_ecom.service.AddressService;
import com.than.project.pop_cart_ecom.util.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@Controller
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AuthUtil authUtil;

    @PostMapping("/saveAddress")
    public ResponseEntity<AddressDTO> saveAddress(@Valid @RequestBody AddressDTO addressDTO){

        MyUser user = authUtil.loggedInUser();
        AddressDTO address = addressService.saveAddress(addressDTO,user);

        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }
}
