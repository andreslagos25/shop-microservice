package com.shop.products.service.impl;

import com.shop.products.controller.dto.StockCreateRequest;
import com.shop.products.exception.ResourceAlreadyExistsException;
import com.shop.products.exception.ResourceNotFoundException;
import com.shop.products.persistence.entity.Product;
import com.shop.products.persistence.entity.Stock;
import com.shop.products.persistence.repository.ProductRepository;
import com.shop.products.persistence.repository.StockRepository;
import com.shop.products.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements IStockService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    StockRepository stockRepository;

    @Override
    public void saveStock(StockCreateRequest stockCreateRequest) {
        stockRepository.findByNameProduct(stockCreateRequest.product().nameProduct())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException("This product already exists in the stock");
                });
        Product product = productRepository.findByNameProduct(stockCreateRequest.product().nameProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "NameProduct", stockCreateRequest.product().nameProduct()));
        Stock stock = Stock.builder()
                .quantity(stockCreateRequest.quantity())
                .reservedQuantity(stockCreateRequest.reservedQuantity())
                .minQuantity(stockCreateRequest.minQuantity())
                .maxQuantity(stockCreateRequest.maxQuantity())
                .product(product)
                .build();
        stockRepository.save(stock);
    }
}
