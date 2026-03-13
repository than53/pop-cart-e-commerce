package com.than.project.pop_cart_ecom.service.impl;

import com.than.project.pop_cart_ecom.exception.APIException;
import com.than.project.pop_cart_ecom.exception.ResourceNotFoundException;
import com.than.project.pop_cart_ecom.model.Category;
import com.than.project.pop_cart_ecom.model.Product;
import com.than.project.pop_cart_ecom.payload.ProductDTO;
import com.than.project.pop_cart_ecom.payload.ProductResponse;
import com.than.project.pop_cart_ecom.repository.CategoryRepository;
import com.than.project.pop_cart_ecom.repository.ProductRepository;
import com.than.project.pop_cart_ecom.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoriesId) {

        Category category = categoryRepository.findById(categoriesId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category not found with id:",categoriesId));

        Product product = modelMapper.map(productDTO, Product.class);

        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProduct() {

        List<Product> productList = productRepository.findAll();

        return getProductResponse(productList);

    }

    @Override
    public ProductResponse searchByCategory(Long categoriesId) {


        Category category = categoryRepository.findById(categoriesId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category not found with id:",categoriesId));

        List<Product> productList = productRepository.findByCategoryOrderByPriceAsc(category);
        return getProductResponse(productList);
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {

        List<Product> productList = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%');

        return  getProductResponse(productList);
    }

    @NonNull
    private ProductResponse getProductResponse(List<Product> productList) {
//        if(productList.isEmpty()){
//            throw new APIException("No Product Records found!!");
//        }
        List<ProductDTO> productDTOS = productList.stream()
                        .map( product -> modelMapper.map(product, ProductDTO.class))
                        .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);

        return productResponse;
    }
}
