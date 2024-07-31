package com.shop.clients.controller;

import com.shop.clients.persistence.entity.ClientEntity;
import com.shop.clients.persistence.entity.dto.ClientDTO;
import com.shop.clients.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/client")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @PostMapping("/create")
    public ResponseEntity saveClient(@RequestBody ClientDTO clientDTO){
        try {

            clientService.saveClient(clientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
