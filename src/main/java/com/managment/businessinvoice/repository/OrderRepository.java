package com.managment.businessinvoice.repository;

import com.managment.businessinvoice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select * from customer_order", nativeQuery = true)
    List<Order> getAll();
}
