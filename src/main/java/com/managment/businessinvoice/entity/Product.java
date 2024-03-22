package com.managment.businessinvoice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    @Column(columnDefinition = "BIGINT")
    private Long price;
    private Integer quantity;
    public Product() {
        quantity = 0; // Default value is set in the constructor
    }
}
