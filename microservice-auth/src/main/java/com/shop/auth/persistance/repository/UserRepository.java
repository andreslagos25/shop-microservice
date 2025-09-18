package com.shop.auth.persistance.repository;

import com.shop.auth.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findUserEntityByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :idUser")
    Optional<UserEntity> findUserById(@Param("idUser") String idUser);
}
