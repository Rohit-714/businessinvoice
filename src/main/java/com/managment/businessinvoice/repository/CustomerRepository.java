package com.managment.businessinvoice.repository;

import com.managment.businessinvoice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByEmailAndPassword(String email,String password);
    Customer getByEmail(String email);


}