package com.shop.products.service;


import com.shop.products.controller.dto.BrandCreateRequest;

public interface IBrandService {
    void saveBrand(BrandCreateRequest brandCreateRequest);
}
