package com.shop.clients.persistence.repository;

import com.shop.clients.persistence.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressEntity, String> {
}
