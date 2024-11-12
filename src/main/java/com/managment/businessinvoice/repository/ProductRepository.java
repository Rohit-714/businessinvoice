package com.managment.businessinvoice.repository;

import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.entity.Order;
import com.managment.businessinvoice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByAdminId(Long adminId);
}
