package com.shop.products.service;

import com.shop.products.controller.dto.ProductCreateRequest;

public interface IProductService {
    void saveProduct(ProductCreateRequest productCreateRequest);
}
