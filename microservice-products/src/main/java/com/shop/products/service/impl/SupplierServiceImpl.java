package com.shop.products.service.impl;

import com.shop.products.controller.dto.request.SupplierCreateRequest;
import com.shop.products.exception.ResourceAlreadyExistsException;
import com.shop.products.persistence.entity.Supplier;
import com.shop.products.persistence.repository.SupplierRepository;
import com.shop.products.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public void saveSupplier(SupplierCreateRequest supplierCreateRequest) {
        supplierRepository.findByNameSupplier(supplierCreateRequest.nameSupplier())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException("Supplier with that name already exists");
                });
        Supplier supplier = Supplier.builder()
                .nameSupplier(supplierCreateRequest.nameSupplier())
                .contactEmail(supplierCreateRequest.contactEmail())
                .phoneNumber(supplierCreateRequest.phoneNumber())
                .address(supplierCreateRequest.address())
                .country(supplierCreateRequest.country())
                .build();

        supplierRepository.save(supplier);
    }
}
