package com.shop.products.service.impl;

import com.shop.products.controller.dto.request.StockCreateRequest;
import com.shop.products.controller.dto.response.*;
import com.shop.products.exception.ResourceAlreadyExistsException;
import com.shop.products.exception.ResourceNotFoundException;
import com.shop.products.exception.ValidationException;
import com.shop.products.persistence.entity.*;
import com.shop.products.persistence.repository.ProductRepository;
import com.shop.products.persistence.repository.StockRepository;
import com.shop.products.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<StockResponse> getStock(){
        return iterableToStockList(stockRepository.findAll());
    }

    public List<StockResponse> iterableToStockList(Iterable<Stock> iterable){
        List<StockResponse> stockList = new ArrayList<>();
        for(Stock stock: iterable){
            stockList.add(new StockResponse(
                    stock.getQuantity(),
                    stock.getReservedQuantity(),
                    stock.getMinQuantity(),
                    stock.getMaxQuantity(),
                    buildProductResponse(stock.getProduct())
            ));
        }

        return stockList;
    }

    public ProductResponse buildProductResponse(Product product){
        return new ProductResponse(
                product.getNameProduct(),
                product.getPrice(),
                product.getDescription(),
                buildSupplierResponse(product.getSupplier()),
                buildBrandResponse(product.getBrand()),
                buildCategoryResponse(product.getCategory()));
    }

    public SupplierResponse buildSupplierResponse(Supplier supplier){
        return new SupplierResponse(
                supplier.getId(),
                supplier.getNameSupplier(),
                supplier.getContactEmail(),
                supplier.getPhoneNumber(),
                supplier.getAddress(),
                supplier.getCountry()
        );
    }

    public BrandResponse buildBrandResponse(Brand brand){
        return new BrandResponse(brand.getIdBrand(), brand.getNameBrand());
    }

    public CategoryResponse buildCategoryResponse(Category category){
        return new CategoryResponse(category.getIdCategory(), category.getNameCategory());
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
