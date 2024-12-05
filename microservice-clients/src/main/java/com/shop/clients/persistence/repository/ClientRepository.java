package com.shop.clients.persistence.repository;

import com.shop.clients.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<ClientEntity, String> {

    @Query("SELECT c FROM ClientEntity c WHERE c.userId= :idUser")
    Optional<ClientEntity> findByUserId(@Param("idUser") String idUser);

}
