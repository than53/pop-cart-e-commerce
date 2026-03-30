package com.than.project.pop_cart_ecom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsDTO {

    private Long cartItemsId;
    private CartDTO cartDTO;
    private ProductDTO productDTO;
    private Integer quantity;
    private Double discount;
    private Double productPrice;
}
