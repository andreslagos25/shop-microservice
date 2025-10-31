package com.shop.products.controller;

import com.shop.products.controller.dto.request.BrandCreateRequest;
import com.shop.products.controller.dto.response.BrandResponse;
import com.shop.products.service.impl.BrandServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/brand")
public class BrandController {

    @Autowired
    BrandServiceImpl brandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveBrand(@Valid @RequestBody BrandCreateRequest brandCreateRequest){
        brandService.saveBrand(brandCreateRequest);
    }

    @GetMapping
    public List<BrandResponse> getAllBrands(){
        return brandService.getAllBrands();
    }
}
