package com.shop.products.service.impl;

import com.shop.products.controller.dto.BrandCreateRequest;
import com.shop.products.persistence.entity.Brand;
import com.shop.products.persistence.repository.BrandRepository;
import com.shop.products.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public void saveBrand(BrandCreateRequest brandCreateRequest) {
        Brand brand = Brand.builder()
                .nameBrand(brandCreateRequest.nameBrand())
                .build();
        brandRepository.save(brand);
    }
}
