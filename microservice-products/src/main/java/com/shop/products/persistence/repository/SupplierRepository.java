package com.shop.products.persistence.repository;

import com.shop.products.persistence.entity.Supplier;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SupplierRepository extends CrudRepository<Supplier, String> {

    Optional<Supplier> findByNameSupplier(String nameSupplier);
}
