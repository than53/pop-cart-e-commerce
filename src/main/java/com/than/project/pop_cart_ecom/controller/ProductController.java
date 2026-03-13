package com.than.project.pop_cart_ecom.controller;

import com.than.project.pop_cart_ecom.payload.ProductDTO;
import com.than.project.pop_cart_ecom.payload.ProductResponse;
import com.than.project.pop_cart_ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

        @PostMapping("/admin/categories/{categoriesId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoriesId){
        ProductDTO addProduct = productService.addProduct(productDTO, categoriesId);
        return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProduct(){
            ProductResponse productList = productService.getAllProduct();

            return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}
