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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getAllAddress")
    public ResponseEntity<List<AddressDTO>> getAllAddress(){

        List<AddressDTO> addresses = addressService.getAllAddress();

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/getUserAddress")
    public ResponseEntity< List<AddressDTO>> getUserAddress(){

        MyUser user = authUtil.loggedInUser();

        List<AddressDTO> addresses = addressService.getUserAddress(user);

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/getAddressById/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable("addressId") Long addressId){

        AddressDTO address = addressService.getAddressById(addressId);

        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PutMapping("/updateAddress/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable("addressId") Long addressId,
                                                    @RequestBody AddressDTO addressDTO){

        MyUser user = authUtil.loggedInUser();

        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO, user);

        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAddress/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable("addressId") Long addressId){

        MyUser user = authUtil.loggedInUser();

        String deletedAddress = addressService.deleteAddress(addressId, user);

        return new ResponseEntity<>(deletedAddress, HttpStatus.OK);
    }
}
