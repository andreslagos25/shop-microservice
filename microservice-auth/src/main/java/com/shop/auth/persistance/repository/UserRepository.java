package com.shop.auth.persistance.repository;

import com.shop.auth.persistance.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findUserEntityByUsername(String username);
}
