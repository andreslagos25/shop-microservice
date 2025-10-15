package com.shop.products.service;


import com.shop.products.controller.dto.CategoryCreateRequest;

public interface ICategoryService {
    void saveCategory(CategoryCreateRequest createRequest);
}
