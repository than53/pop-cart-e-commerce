package com.than.project.pop_cart_ecom.service;

import com.than.project.pop_cart_ecom.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long id);
    Category updateCategory(Long id, Category category);
}
