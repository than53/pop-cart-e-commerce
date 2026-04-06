package com.than.project.pop_cart_ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="addresses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private Long addressId;

    private String street;

    private String buildingName;

    @NotBlank
    @Size(min=2, message="Baranggay name must be atleast 2 characters")
    private String baranggay;

    @NotBlank
    @Size(min=2, message="City name must be atleast 2 characters")
    private String city;

    @NotBlank
    @Size(min=2, message="Province name must be atleast 2 characters")
    private String province;

    @NotBlank
    @Size(min=2, message="Country name must be atleast 2 characters")
    private String country;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    public Address(String street, String buildingName, String baranggay, String city, String province, String country) {
        this.street = street;
        this.buildingName = buildingName;
        this.baranggay = baranggay;
        this.city = city;
        this.province = province;
        this.country = country;
    }
}
