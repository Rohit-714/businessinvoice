package com.managment.businessinvoice.dto;

import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.entity.Invoice;
import com.managment.businessinvoice.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;

    private Long totalAmount;
    private Customer customer; // Reference to the customer who placed the order
    private Date invoiceDate;
    private Invoice invoice;


    private List<Product> products;
    private List<Integer> quantity;
    // @Column(name = "product_id") // Assuming product_id is the column name for product ID
    private List<Long> productId;
}
