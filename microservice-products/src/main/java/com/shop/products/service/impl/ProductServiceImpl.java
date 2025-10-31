package com.shop.products.service.impl;

import com.shop.products.controller.dto.request.ProductCreateRequest;
import com.shop.products.exception.ResourceAlreadyExistsException;
import com.shop.products.exception.ResourceNotFoundException;
import com.shop.products.persistence.entity.Brand;
import com.shop.products.persistence.entity.Category;
import com.shop.products.persistence.entity.Product;
import com.shop.products.persistence.entity.Supplier;
import com.shop.products.persistence.repository.BrandRepository;
import com.shop.products.persistence.repository.CategoryRepository;
import com.shop.products.persistence.repository.ProductRepository;
import com.shop.products.persistence.repository.SupplierRepository;
import com.shop.products.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void saveProduct(ProductCreateRequest productCreateRequest) {
        Brand brand = brandRepository.findByNameBrand(productCreateRequest.brand().nameBrand())
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "nameBrand", productCreateRequest.brand().nameBrand()));
        Category category = categoryRepository.findByNameCategory(productCreateRequest.category().nameCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "nameCategory", productCreateRequest.category().nameCategory()));
        Supplier supplier = supplierRepository.findByNameSupplier(productCreateRequest.supplier().nameSupplier())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "nameSupplier", productCreateRequest.supplier().nameSupplier()));
        productRepository.findByNameProduct(productCreateRequest.nameProduct())
                .ifPresent(exists -> {
                    throw new ResourceAlreadyExistsException("Cannot create two products with the same name");
                });
        Product product = Product.builder()
                .nameProduct(productCreateRequest.nameProduct())
                .price(productCreateRequest.price())
                .description(productCreateRequest.description())
                .supplier(supplier)
                .brand(brand)
                .category(category)
                .cratedAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        productRepository.save(product);
    }
}
