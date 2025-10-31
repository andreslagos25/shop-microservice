package com.shop.products.service;


import com.shop.products.controller.dto.request.CategoryCreateRequest;
import com.shop.products.controller.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    void saveCategory(CategoryCreateRequest createRequest);

    List<CategoryResponse> getAllCategories();
}
