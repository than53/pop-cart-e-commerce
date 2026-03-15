package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.payload.ProductDTO;
import com.than.project.pop_cart_ecom.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductDTO addProduct(ProductDTO productDTO, Long categoriesId);

    ProductResponse getAllProduct();

    ProductResponse searchByCategory(Long categoriesId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
