package com.shop.products.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 50, nullable = false)
    private String nameSupplier;

    @Column(length = 30, nullable = false)
    private String contactEmail;

    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 80, nullable = false)
    private String address;
    @Column(length = 50, nullable = false)
    private String country;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;


}
