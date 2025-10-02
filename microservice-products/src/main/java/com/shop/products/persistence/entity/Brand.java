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
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idBrand;

    @Column(length = 50, nullable = false, unique = true)
    private String nameBrand;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
