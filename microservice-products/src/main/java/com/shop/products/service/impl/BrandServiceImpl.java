package com.shop.products.service.impl;

import com.shop.products.controller.dto.BrandCreateRequest;
import com.shop.products.controller.dto.BrandResponse;
import com.shop.products.exception.ResourceAlreadyExistsException;
import com.shop.products.persistence.entity.Brand;
import com.shop.products.persistence.repository.BrandRepository;
import com.shop.products.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public void saveBrand(BrandCreateRequest brandCreateRequest) {
        brandRepository.findByNameBrand(brandCreateRequest.nameBrand())
                .ifPresent(exists -> {
                    throw new ResourceAlreadyExistsException("There is already a brand with the same name");
                });
        Brand brand = Brand.builder()
                .nameBrand(brandCreateRequest.nameBrand())
                .build();
        brandRepository.save(brand);
    }

    @Override
    public List<BrandResponse> getAllBrands(){
        return iterableBrandToArray(brandRepository.findAll());
    }

    public List<BrandResponse> iterableBrandToArray(Iterable<Brand> iterable){
        List<BrandResponse> brandResponseArrayList = new ArrayList<>();
        for (Brand brand: iterable){
            brandResponseArrayList.add(new BrandResponse(brand.getIdBrand(), brand.getNameBrand()));
        }
        return brandResponseArrayList;
    }

}
