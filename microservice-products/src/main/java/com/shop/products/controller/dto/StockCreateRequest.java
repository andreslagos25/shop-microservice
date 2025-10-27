package com.shop.products.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record StockCreateRequest(
        @NotNull @Min(0) Integer quantity,
        @NotNull @Min(0) Integer reservedQuantity,
        @NotNull @Min(1) Integer minQuantity,
        @Min(1) Integer maxQuantity,
        @NotNull ProductCreateRequest product
) {
}
