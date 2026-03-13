package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.payload.ProductDTO;

public interface ProductService {

    ProductDTO addProduct(ProductDTO productDTO, Long categoriesId);
}
