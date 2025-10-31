package com.shop.products.controller.dto.response;

import com.shop.products.controller.dto.request.BrandCreateRequest;
import com.shop.products.controller.dto.request.CategoryCreateRequest;
import com.shop.products.controller.dto.request.SupplierCreateRequest;

public record ProductResponse(
        String nameProduct,
        Double price,
        String description,
        SupplierCreateRequest supplier,
        BrandResponse brand,
        CategoryResponse category
) {
}
