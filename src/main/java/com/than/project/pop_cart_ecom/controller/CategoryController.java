package com.than.project.pop_cart_ecom.controller;

import com.than.project.pop_cart_ecom.model.Category;
import com.than.project.pop_cart_ecom.payload.CategoryDTO;
import com.than.project.pop_cart_ecom.payload.CategoryResponse;
import com.than.project.pop_cart_ecom.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse>getAllCategories(){
        CategoryResponse categoriesResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoriesResponse, HttpStatus.OK);
    }


    @PostMapping("/public/createCategory")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/public/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){

            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/public/updateCategory/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable long id,@Valid @RequestBody Category category){
            Category updatedCategory = categoryService.updateCategory(id, category);
            return new ResponseEntity<>("Successfully update" + category,HttpStatus.OK);
    }
}
