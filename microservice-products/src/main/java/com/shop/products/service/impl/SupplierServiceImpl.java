package com.shop.products.service.impl;

import com.shop.products.controller.dto.request.SupplierCreateRequest;
import com.shop.products.controller.dto.response.CategoryResponse;
import com.shop.products.controller.dto.response.SupplierResponse;
import com.shop.products.exception.ResourceAlreadyExistsException;
import com.shop.products.persistence.entity.Supplier;
import com.shop.products.persistence.repository.SupplierRepository;
import com.shop.products.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return iterableToSupplerList(supplierRepository.findAll());
    }

    public List<SupplierResponse> iterableToSupplerList(Iterable<Supplier> iterable){
        List<SupplierResponse> supplierList = new ArrayList<>();
        for(Supplier supplier: iterable){
            supplierList.add(
                    new SupplierResponse(
                            supplier.getId(),
                            supplier.getNameSupplier(),
                            supplier.getContactEmail(),
                            supplier.getPhoneNumber(),
                            supplier.getAddress(),
                            supplier.getCountry())
            );
        }
        return supplierList;
    }
}
