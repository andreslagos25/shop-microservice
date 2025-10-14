package com.shop.products.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SupplierCreateRequest(
        @NotBlank String nameSupplier,
        @Email @NotBlank String contactEmail,
        @NotBlank String phoneNumber,
        @NotBlank String address,
        @NotBlank String country
){
}
