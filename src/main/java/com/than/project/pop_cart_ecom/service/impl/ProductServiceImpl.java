package com.than.project.pop_cart_ecom.service.impl;

import com.than.project.pop_cart_ecom.exception.APIException;
import com.than.project.pop_cart_ecom.exception.ResourceNotFoundException;
import com.than.project.pop_cart_ecom.model.Category;
import com.than.project.pop_cart_ecom.model.Product;
import com.than.project.pop_cart_ecom.payload.ProductDTO;
import com.than.project.pop_cart_ecom.payload.ProductResponse;
import com.than.project.pop_cart_ecom.repository.CategoryRepository;
import com.than.project.pop_cart_ecom.repository.ProductRepository;
import com.than.project.pop_cart_ecom.service.FileService;
import com.than.project.pop_cart_ecom.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final FileService fileService;

    private final ModelMapper modelMapper;

    @Value("${project.images.path}")
    private String imageFolderPath;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoriesId) {

        Category category = categoryRepository.findById(categoriesId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId:",categoriesId));

        boolean isProductNotPresent = true;

        List<Product> productList = category.getProducts();
        for(Product products:productList){
            if(products.getProductName().equals(productDTO.getProductName())){
                isProductNotPresent = false;
                break;
            }
        }

        if(isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);

            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);

            return modelMapper.map(product, ProductDTO.class);
        }else{
            throw new APIException("Product Already Exist!!");
        }
    }

    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndDir = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sortByAndDir);

        Page<Product> productPage = productRepository.findAll(pageable);

        List<Product> productList = productPage.getContent();

        return getProductResponse(productList,productPage);

    }

    @Override
    public ProductResponse searchByCategory(Long categoriesId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Category category = categoryRepository.findById(categoriesId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId:",categoriesId));

        Sort sortByAndDir = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sortByAndDir);

        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category, pageable);

        List<Product> productList = productPage.getContent();

        return getProductResponse(productList,productPage);
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndDir = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sortByAndDir);

        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%', pageable);

        List<Product> productList = productPage.getContent();

        return getProductResponse(productList,productPage);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {

        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product: ", "productId:",productId));

        productFromDb.setProductName(productDTO.getProductName());
        productFromDb.setDescription(productDTO.getDescription());
        productFromDb.setQuantity(productDTO.getQuantity());
        productFromDb.setDiscount(productDTO.getDiscount());
        productFromDb.setPrice(productDTO.getPrice());
        double specialPrice = productDTO.getPrice() - ((productDTO.getDiscount() * 0.01) * productDTO.getPrice());
        productFromDb.setSpecialPrice(specialPrice);


        Product updatedProduct = productRepository.save(productFromDb);

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product: ", "productId:",productId));

        productRepository.delete(productFromDb);

        return modelMapper.map(productFromDb, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {

        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));

        //Upload image to specific folder in server and get the filename of image
        String path = imageFolderPath;
        String filename = fileService.uploadImage(path, image);

        productFromDb.setImage(filename);
        Product updatedProduct = productRepository.save(productFromDb);

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }


    @NonNull
    private ProductResponse getProductResponse(List<Product> productList, Page<Product> productPage) {

        List<ProductDTO> productDTOS = productList.stream()
                        .map( product -> modelMapper.map(product, ProductDTO.class))
                        .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }
}
