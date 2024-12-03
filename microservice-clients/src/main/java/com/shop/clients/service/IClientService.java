package com.shop.clients.service;

import com.shop.clients.persistence.entity.ClientEntity;
import com.shop.clients.persistence.entity.dto.ClientDTO;

import java.util.List;

public interface IClientService {

    List<ClientEntity> findAll();
    void saveClient(ClientDTO clientDTO);
    ClientEntity findById(String id);
    void deleteUser(String emailClient);
}
