package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.payload.ProductDTO;
import com.than.project.pop_cart_ecom.payload.ProductResponse;

public interface ProductService {

    ProductDTO addProduct(ProductDTO productDTO, Long categoriesId);

    ProductResponse getAllProduct();

    ProductResponse searchByCategory(Long categoriesId);

    ProductResponse searchProductByKeyword(String keyword);
}
