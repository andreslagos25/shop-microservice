package com.shop.products.persistence.repository;

import com.shop.products.persistence.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {
    Optional<Category> findByNameCategory(String nameCategory);
}
