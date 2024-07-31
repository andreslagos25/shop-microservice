package com.shop.clients.persistence.entity.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private String address;
    private String complAddress;
    private String city;
    private String country;
    private String postalCode;
}
