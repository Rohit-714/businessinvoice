package com.managment.businessinvoice.repository;

import com.managment.businessinvoice.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByUserNameAndPassword(String username, String password);
    Admin findByUserName(String username);
}
