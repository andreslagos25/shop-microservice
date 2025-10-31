package com.shop.products.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank String nameCategory
) {
}
