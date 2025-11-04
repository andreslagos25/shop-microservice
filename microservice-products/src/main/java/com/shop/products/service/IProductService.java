package com.shop.products.service;

import com.shop.products.controller.dto.request.ProductCreateRequest;
import com.shop.products.controller.dto.response.ProductResponse;

import java.util.List;

public interface IProductService {
    void saveProduct(ProductCreateRequest productCreateRequest);
    List<ProductResponse> getAllProducts();
}
