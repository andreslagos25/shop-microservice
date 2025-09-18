package com.shop.clients.persistence.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {

    @NotNull(message = "Name cannot be null")
    private String nameClient;
    @NotNull(message = "Lastname cannot be null")
    private String lastnameClient;
    @Email(message = "Email must be valid")
    private String email;
    @Size(min = 10, max = 12, message = "Phone number is not valid")
    private String phone;
    @NotNull
    private Set<AddressDTO> addressSet;
    @NotNull
    private String userId;
}
