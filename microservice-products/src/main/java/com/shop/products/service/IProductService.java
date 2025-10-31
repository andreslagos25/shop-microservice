package com.shop.products.service;

import com.shop.products.controller.dto.request.ProductCreateRequest;

public interface IProductService {
    void saveProduct(ProductCreateRequest productCreateRequest);
}
