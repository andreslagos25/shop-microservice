package com.shop.products.service.impl;


import com.shop.products.controller.dto.CategoryCreateRequest;
import com.shop.products.persistence.entity.Category;
import com.shop.products.persistence.repository.CategoryRepository;
import com.shop.products.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void saveCategory(CategoryCreateRequest createRequest) {
        Category category = Category.builder()
                .nameCategory(createRequest.nameCategory())
                .build();
        categoryRepository.save(category);
    }
}
