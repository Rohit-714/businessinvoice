package com.managment.businessinvoice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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
    private Integer buyedQuantity;
    public Product() {
        quantity = 0;
        buyedQuantity=0;// Default value is set in the constructor
    }
}
