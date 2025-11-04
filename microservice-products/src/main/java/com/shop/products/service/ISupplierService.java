package com.shop.products.service;

import com.shop.products.controller.dto.request.SupplierCreateRequest;
import com.shop.products.controller.dto.response.SupplierResponse;

import java.util.List;

public interface ISupplierService {

    void saveSupplier(SupplierCreateRequest supplierCreateRequest);
    List<SupplierResponse> getAllSuppliers();
}
