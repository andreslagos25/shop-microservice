package com.shop.products.persistence.repository;

import com.shop.products.persistence.entity.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends CrudRepository<Brand, String> {
    Optional<Brand> findByNameBrand(String nameBrand);
}
