package com.than.project.pop_cart_ecom.service.impl;

import com.than.project.pop_cart_ecom.exception.APIException;
import com.than.project.pop_cart_ecom.exception.ResourceNotFoundException;
import com.than.project.pop_cart_ecom.model.Category;
import com.than.project.pop_cart_ecom.payload.CategoryDTO;
import com.than.project.pop_cart_ecom.payload.CategoryResponse;
import com.than.project.pop_cart_ecom.repository.CategoryRepository;
import com.than.project.pop_cart_ecom.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();

        if(categoryList.isEmpty()){
            throw new APIException("No records found!!");
        }

        List<CategoryDTO> categoryDTOS = categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse response = new CategoryResponse();
        response.setContent(categoryDTOS);
        return response;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category =  modelMapper.map(categoryDTO, Category.class);
        Category foundCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());

        if(foundCategory !=null){
            throw new APIException("Category with the name: " + category.getCategoryName() + " already Existing!!");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
      categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {

        Category savedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("categorygi", "categoryId", id));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(id);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
