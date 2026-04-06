package com.than.project.pop_cart_ecom.payload;

import com.than.project.pop_cart_ecom.model.MyUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {


    private String street;

    private String buildingName;

    @NotBlank
    @Size(min=2, message="City name must be atleast 2 characters")
    private String baranggay;

    @NotBlank
    @Size(min=2, message="City name must be atleast 2 characters")
    private String city;

    @NotBlank
    @Size(min=2, message="State name must be atleast 2 characters")
    private String province;

    @NotBlank
    @Size(min=2, message="Country name must be atleast 2 characters")
    private String country;
}
