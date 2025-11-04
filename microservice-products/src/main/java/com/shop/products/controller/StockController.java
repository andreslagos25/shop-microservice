package com.shop.products.controller;

import com.shop.products.controller.dto.request.StockCreateRequest;
import com.shop.products.controller.dto.response.StockResponse;
import com.shop.products.service.impl.StockServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product/stock")
public class StockController {

    @Autowired
    private StockServiceImpl stockService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStock(@Valid @RequestBody StockCreateRequest stockCreateRequest){
        stockService.saveStock(stockCreateRequest);
    }

    @GetMapping
    public List<StockResponse> getStock(){
        return stockService.getStock();
    }
}
