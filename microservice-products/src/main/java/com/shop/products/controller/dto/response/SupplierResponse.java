package com.shop.products.controller.dto.response;


public record SupplierResponse(
    String idSupplier,
    String nameSupplier,
    String contactEmail,
    String phoneNumber,
    String address,
    String country
){
}
