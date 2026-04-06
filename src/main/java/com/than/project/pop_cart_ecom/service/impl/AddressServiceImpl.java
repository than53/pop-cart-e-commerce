package com.than.project.pop_cart_ecom.service.impl;

import com.than.project.pop_cart_ecom.exception.ResourceNotFoundException;
import com.than.project.pop_cart_ecom.model.Address;
import com.than.project.pop_cart_ecom.model.MyUser;
import com.than.project.pop_cart_ecom.payload.AddressDTO;
import com.than.project.pop_cart_ecom.repository.AddressRepository;
import com.than.project.pop_cart_ecom.repository.MyUserRepository;
import com.than.project.pop_cart_ecom.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final MyUserRepository userRepository;

    private final static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
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

    @Override
    public List<AddressDTO> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();

        return addresses.stream().map((address)
                ->modelMapper.map(address, AddressDTO.class)).toList();
    }

    @Override
    public  List<AddressDTO> getUserAddress(MyUser user) {

        List<Address> userAddress = user.getAddresses();

        return userAddress.stream().map((address)
                ->modelMapper.map(address, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));

        return modelMapper.map(address, AddressDTO.class);

    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO, MyUser user) {

        List<Address> userAddress = user.getAddresses();

        Address addressFromDb = userAddress.stream()
                .filter(add -> add.getAddressId().equals(addressId))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));

        addressFromDb.setStreet(addressDTO.getStreet());
        addressFromDb.setBuildingName(addressDTO.getBuildingName());
        addressFromDb.setBaranggay(addressDTO.getBaranggay());
        addressFromDb.setCity(addressDTO.getCity());
        addressFromDb.setProvince(addressDTO.getProvince());
        addressFromDb.setCountry(addressDTO.getCountry());

        Address updatedAddress = addressRepository.save(addressFromDb);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

        @Override
        public String deleteAddress(Long addressId, MyUser user) {
            try{

                List<Address> userAddress = user.getAddresses();

                Address addressFromDb = userAddress.stream()
                        .filter(add -> add.getAddressId().equals(addressId))
                        .findFirst()
                        .orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));

                userAddress.remove(addressFromDb);

                user.setAddresses(userAddress);

                userRepository.save(user);
                return "Successfully deleted address with ID: " + addressId;
            }catch (Exception e){
                logger.error("Error: {}", e.getMessage());
                return  e.getMessage();
            }



    }
}
