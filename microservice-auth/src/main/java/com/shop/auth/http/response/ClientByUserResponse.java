package com.shop.auth.http.response;

import com.shop.auth.controller.dto.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientByUserResponse {
    private String username;
    private ClientDTO clientDTO;
}
