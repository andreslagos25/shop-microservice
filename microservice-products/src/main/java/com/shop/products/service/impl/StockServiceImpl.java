package com.shop.products.service.impl;

import com.shop.products.controller.dto.request.StockCreateRequest;
import com.shop.products.exception.ResourceAlreadyExistsException;
import com.shop.products.exception.ResourceNotFoundException;
import com.shop.products.exception.ValidationException;
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
        validateStockNotExist(stockCreateRequest);
        Product product = getExistingProducts(stockCreateRequest);
        validateQuantities(stockCreateRequest);
        stockRepository.save(buildStockFromRequest(stockCreateRequest, product));
    }

    public void validateStockNotExist(StockCreateRequest stockCreateRequest){
        stockRepository.findByNameProduct(stockCreateRequest.product().nameProduct())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException("This product already exists in the stock");
                });
    }

    public Product getExistingProducts(StockCreateRequest stockCreateRequest){
        return productRepository.findByNameProduct(stockCreateRequest.product().nameProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "NameProduct", stockCreateRequest.product().nameProduct()));
    }

    public void validateQuantities(StockCreateRequest stockCreateRequest){
        if(stockCreateRequest.quantity() < stockCreateRequest.reservedQuantity()) throw new ValidationException("The reserved quantity cannot be more than the quantity in stock");
    }
    public Stock buildStockFromRequest(StockCreateRequest stockCreateRequest, Product product){
        return Stock.builder()
                .quantity(stockCreateRequest.quantity())
                .reservedQuantity(stockCreateRequest.reservedQuantity())
                .minQuantity(stockCreateRequest.minQuantity())
                .maxQuantity(stockCreateRequest.maxQuantity())
                .product(product)
                .build();
    }
}
