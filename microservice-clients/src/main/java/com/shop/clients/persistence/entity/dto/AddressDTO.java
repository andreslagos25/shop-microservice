package com.shop.clients.persistence.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    @NotNull(message = "Address cannot be null")
    private String address;
    private String complAddress;
    @NotNull(message = "City cannot be null")
    private String city;
    @NotNull(message = "Country cannot be null")
    private String country;
    @NotNull(message = "Postal code cannot be null")
    private String postalCode;
}
