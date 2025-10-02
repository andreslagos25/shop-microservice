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
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idCategory;

    @Column(length = 50, unique = true)
    private String nameCategory;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
