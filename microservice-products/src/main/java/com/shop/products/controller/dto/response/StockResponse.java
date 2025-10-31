package com.shop.products.controller.dto.response;

import com.shop.products.controller.dto.request.ProductCreateRequest;


public record StockResponse(
        Integer quantity,
        Integer reservedQuantity,
        Integer minQuantity,
        Integer maxQuantity,
        ProductResponse product
) {
}
