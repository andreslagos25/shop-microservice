package com.shop.products.persistence.repository;

import com.shop.products.persistence.entity.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends CrudRepository<Stock, String> {
    @Query("SELECT s FROM Stock s WHERE s.product.nameProduct = :nameProduct")
    Optional<Stock> findByNameProduct(@Param("nameProduct") String nameProduct);
}
