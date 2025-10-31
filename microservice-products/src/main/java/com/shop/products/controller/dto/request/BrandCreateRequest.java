package com.shop.products.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BrandCreateRequest(
        @NotBlank String nameBrand
) {
}
