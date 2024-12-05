package com.shop.clients.controller;

import com.shop.clients.persistence.entity.dto.ClientDTO;
import com.shop.clients.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("/update")
    public ResponseEntity updateClient(@RequestBody ClientDTO clientDTO, @RequestParam String idClient){
        try {
            return ResponseEntity.ok().body("UPDATE");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/search-by-user/{idUser}")
    public ResponseEntity<?> findByIdUser(@PathVariable String idUser){
        return ResponseEntity.ok(clientService.findByUserId(idUser));
    }

    @DeleteMapping("/delete-user/{idUser}")
    public ResponseEntity deleteUser(@PathVariable String idUser){
        clientService.deleteUser(idUser);
        return ResponseEntity.ok("User deleted successfully");
    }
}
