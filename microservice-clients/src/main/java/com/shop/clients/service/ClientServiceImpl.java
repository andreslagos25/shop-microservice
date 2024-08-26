package com.shop.clients.service;

import com.shop.clients.persistence.entity.AddressEntity;
import com.shop.clients.persistence.entity.ClientEntity;
import com.shop.clients.persistence.entity.dto.AddressDTO;
import com.shop.clients.persistence.entity.dto.ClientDTO;
import com.shop.clients.persistence.repository.AddressRepository;
import com.shop.clients.persistence.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ClientServiceImpl implements IClientService{

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public List<ClientEntity> findAll() {
        return (List<ClientEntity>) clientRepository.findAll();
    }

    @Override
    public void saveClient(ClientDTO clientDTO) {
        if(Objects.isNull(clientDTO.getAddressSet())){
            throw new IllegalArgumentException("You must have a least one address");
        }
        ClientEntity client = ClientEntity.builder()
                .nameClient(clientDTO.getNameClient())
                .lastnameClient(clientDTO.getLastnameClient())
                .email(clientDTO.getEmail())
                .phone(clientDTO.getPhone())
                .build();
        client = clientRepository.save(client);
        for(AddressDTO addressDTO : clientDTO.getAddressSet()){
            AddressEntity address = AddressEntity.builder()
                    .address(addressDTO.getAddress())
                    .complAddress(addressDTO.getComplAddress())
                    .city(addressDTO.getCity())
                    .country(addressDTO.getCountry())
                    .postalCode(addressDTO.getPostalCode())
                    .client(client)
                    .build();
            addressRepository.save(address);
        }
    }


    @Override
    public ClientEntity findById(String id) {
        return clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
    }
}
