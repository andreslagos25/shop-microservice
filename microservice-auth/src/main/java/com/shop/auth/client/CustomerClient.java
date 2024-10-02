package com.shop.auth.client;

import com.shop.auth.controller.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-clients", url = "localhost:8080")
public interface CustomerClient {

    @GetMapping("api/client/search-by-user/{idUser}")
    ClientDTO findClientByUser(@PathVariable String idUser);
}
