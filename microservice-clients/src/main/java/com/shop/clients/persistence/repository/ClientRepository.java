package com.shop.clients.persistence.repository;

import com.shop.clients.persistence.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity, String> {
}
