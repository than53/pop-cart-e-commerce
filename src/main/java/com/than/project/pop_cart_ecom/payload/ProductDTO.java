package com.than.project.pop_cart_ecom.payload;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;
    @NotNull(message = "Product name is required")
    @Size(min=3, message = "Product name should be at least 3 characters")
    private String productName;
    private String image;
    private String description;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Product Quantity should be at least 1")
    private Integer quantity;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01")
    @Digits(integer = 10, fraction = 2)
    private double price;
    private double discount;
    private double specialPrice;

}
