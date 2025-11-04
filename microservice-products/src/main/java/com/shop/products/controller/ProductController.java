package com.shop.products.controller;

import com.shop.products.controller.dto.request.ProductCreateRequest;
import com.shop.products.controller.dto.response.ProductResponse;
import com.shop.products.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest){
        productService.saveProduct(productCreateRequest);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
