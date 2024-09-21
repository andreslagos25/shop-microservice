package com.shop.clients.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idClient;
    @Column(name = "name_client", length = 40, nullable = false)
    private String nameClient;
    @Column(name = "lastname_client", length = 40, nullable = false)
    private String lastnameClient;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<AddressEntity> addressSet = new HashSet<>();

}
