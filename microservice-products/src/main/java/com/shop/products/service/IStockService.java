package com.shop.products.service;

import com.shop.products.controller.dto.request.StockCreateRequest;
import com.shop.products.controller.dto.response.StockResponse;

import java.util.List;

public interface IStockService {

    void saveStock(StockCreateRequest stockCreateRequest);
    List<StockResponse> getStock();
}
