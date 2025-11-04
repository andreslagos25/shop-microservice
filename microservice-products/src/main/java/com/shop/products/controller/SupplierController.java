package com.shop.products.controller;

import com.shop.products.controller.dto.request.SupplierCreateRequest;
import com.shop.products.controller.dto.response.SupplierResponse;
import com.shop.products.service.impl.SupplierServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product/supplier")
public class SupplierController {

    @Autowired
    private SupplierServiceImpl supplierService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveSupplier(@Valid @RequestBody SupplierCreateRequest supplierCreateRequest){
        supplierService.saveSupplier(supplierCreateRequest);
    }

    @GetMapping
    public List<SupplierResponse> getAllSuppliers(){
        return supplierService.getAllSuppliers();
    }
}
