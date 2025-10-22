package com.shop.products.persistence.repository;

import com.shop.products.persistence.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, String> {
    Optional<Product> findByNameProduct(String nameProduct);
}
