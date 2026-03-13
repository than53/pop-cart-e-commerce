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
            ProductResponse productResponse = productService.getAllProduct();

            return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categories}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categories){
        ProductResponse productResponse = productService.searchByCategory(categories);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse>getProductByKeyword(@PathVariable String keyword){

            ProductResponse productResponse = productService.searchProductByKeyword(keyword);

            return  new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

        @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId){

            ProductDTO updateProduct = productService.updateProduct(productDTO, productId);

            return new ResponseEntity<>(updateProduct, HttpStatus.OK);

    }

}
