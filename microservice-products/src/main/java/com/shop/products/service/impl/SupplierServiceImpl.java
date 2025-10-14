package com.shop.products.service.impl;

import com.shop.products.controller.dto.SupplierCreateRequest;
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
