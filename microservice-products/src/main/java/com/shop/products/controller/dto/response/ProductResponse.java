package com.shop.products.controller.dto.response;

public record ProductResponse(
        String nameProduct,
        Double price,
        String description,
        SupplierResponse supplier,
        BrandResponse brand,
        CategoryResponse category
) {
}
