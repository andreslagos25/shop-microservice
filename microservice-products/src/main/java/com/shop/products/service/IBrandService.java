package com.shop.products.service;


import com.shop.products.controller.dto.BrandCreateRequest;
import com.shop.products.controller.dto.BrandResponse;

import java.util.List;

public interface IBrandService {
    void saveBrand(BrandCreateRequest brandCreateRequest);
    List<BrandResponse> getAllBrands();
}
