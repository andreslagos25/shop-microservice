package com.shop.products.controller.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

public record ProductCreateRequest(
        @NotBlank String nameProduct,
        @NotNull Double price,
        String description,
        @NotNull SupplierCreateRequest supplier,
        @NotNull BrandCreateRequest brand,
        @NotNull CategoryCreateRequest category
) {
}
