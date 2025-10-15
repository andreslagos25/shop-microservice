package com.shop.products.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank String nameCategory
) {
}
