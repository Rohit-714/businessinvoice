package com.managment.businessinvoice.dto;

import lombok.Data;

@Data
public class ProductsDto {
    private String name;
    private Integer price;
    private String brand;
    private Long productId;
    private Integer quantity;
}
