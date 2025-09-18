package com.shop.clients.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idAddress;
    @Column(name = "address", length = 50, nullable = false)
    private String address;
    @Column(name = "compl_address", length = 25, nullable = true)
    private String complAddress;
    @Column(name = "city", length = 50, nullable = false)
    private String city;
    @Column(name = "country", length = 50, nullable = false)
    private String country;
    @Column(name = "postal_code", length = 10, nullable = false)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private ClientEntity client;
}


