package com.shop.products.controller.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreateRequest(
        @NotBlank String nameProduct,
        @NotNull Double price,
        String description,
        @NotNull SupplierCreateRequest supplier,
        @NotNull BrandCreateRequest brand,
        @NotNull CategoryCreateRequest category
) {
}
