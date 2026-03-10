package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.model.Category;
import com.than.project.pop_cart_ecom.payload.CategoryDTO;
import com.than.project.pop_cart_ecom.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
}
