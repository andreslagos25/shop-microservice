package com.shop.clients.persistence.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

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

    /*
    * SOBREESCRIBIR EL METODO equals y hashCode para
    * Que el set Funcione correctamente
    * */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        AddressDTO addressDTO = (AddressDTO) obj;
        return Objects.equals(this.address, addressDTO.address) &&
                Objects.equals(this.complAddress, addressDTO.complAddress) &&
                Objects.equals(this.city, addressDTO.city) &&
                Objects.equals(this.country, addressDTO.country) &&
                Objects.equals(this.postalCode, addressDTO.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, complAddress,city, country, postalCode);
    }
}
