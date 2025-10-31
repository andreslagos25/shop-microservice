package com.shop.products.service;

import com.shop.products.controller.dto.request.StockCreateRequest;

public interface IStockService {

    void saveStock(StockCreateRequest stockCreateRequest);
}
