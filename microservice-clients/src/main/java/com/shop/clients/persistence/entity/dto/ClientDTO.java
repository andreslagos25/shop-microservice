package com.shop.clients.persistence.entity.dto;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {

    private String nameClient;
    private String lastnameClient;
    private String email;
    private String phone;
    private Set<AddressDTO> addressSet;
}
