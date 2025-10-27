package com.shop.products.service;

import com.shop.products.controller.dto.StockCreateRequest;

public interface IStockService {

    void saveStock(StockCreateRequest stockCreateRequest);
}
