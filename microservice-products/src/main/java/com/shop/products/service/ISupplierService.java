package com.shop.products.service;

import com.shop.products.controller.dto.SupplierCreateRequest;

public interface ISupplierService {

    void saveSupplier(SupplierCreateRequest supplierCreateRequest);
}
