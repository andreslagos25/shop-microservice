package com.shop.products.controller;

import com.shop.products.controller.dto.request.CategoryCreateRequest;
import com.shop.products.controller.dto.response.CategoryResponse;
import com.shop.products.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product/category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCategory(@Valid @RequestBody CategoryCreateRequest createRequest){
        categoryService.saveCategory(createRequest);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
