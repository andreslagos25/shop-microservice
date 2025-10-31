package com.shop.products.service.impl;


import com.shop.products.controller.dto.request.CategoryCreateRequest;
import com.shop.products.controller.dto.response.CategoryResponse;
import com.shop.products.exception.ResourceAlreadyExistsException;
import com.shop.products.persistence.entity.Category;
import com.shop.products.persistence.repository.CategoryRepository;
import com.shop.products.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void saveCategory(CategoryCreateRequest createRequest) {
        categoryRepository.findByNameCategory(createRequest.nameCategory())
                .ifPresent(exists ->{
                    throw new ResourceAlreadyExistsException("Cannot create two categories with the same name");
                });
        Category category = Category.builder()
                .nameCategory(createRequest.nameCategory())
                .build();
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories(){
        return iterableToArrayCategory(categoryRepository.findAll());
    }

    public List<CategoryResponse> iterableToArrayCategory(Iterable<Category> iterable){
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for (Category category: iterable){
            categoryResponseList.add(new CategoryResponse(category.getIdCategory(), category.getNameCategory()));
        }
        return categoryResponseList;
    }
}
