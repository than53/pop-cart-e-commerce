package com.than.project.pop_cart_ecom.service.impl;

import com.than.project.pop_cart_ecom.exception.APIException;
import com.than.project.pop_cart_ecom.exception.ResourceNotFoundException;
import com.than.project.pop_cart_ecom.model.Category;
import com.than.project.pop_cart_ecom.repository.CategoryRepository;
import com.than.project.pop_cart_ecom.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;



    @Override
    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();

        if(categoryList == null || categoryList.isEmpty()){
            throw new APIException("No records found!!");
        }
        return categoryList;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if(savedCategory !=null){
            throw new APIException("Category with the name: " + category.getCategoryName() + " already Existing!!");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
        categoryRepository.delete(category);
        return "Category id: " + id + " deleted successfully";
    }

    @Override
    public Category updateCategory(Long id, Category category) {

        Category savedCataegory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("categorygi", "categoryId", id));

        category.setCategoryId(id);
        savedCataegory = categoryRepository.save(category);
        return savedCataegory;
    }
}
