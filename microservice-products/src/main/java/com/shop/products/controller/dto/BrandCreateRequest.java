package com.shop.products.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record BrandCreateRequest(
        @NotBlank String nameBrand
) {
}
