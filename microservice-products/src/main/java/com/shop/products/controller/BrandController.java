package com.shop.products.controller;

import com.shop.products.controller.dto.BrandCreateRequest;
import com.shop.products.service.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product/brand")
public class BrandController {

    @Autowired
    BrandServiceImpl brandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveBrand(@RequestBody BrandCreateRequest brandCreateRequest){
        brandService.saveBrand(brandCreateRequest);
    }
}
