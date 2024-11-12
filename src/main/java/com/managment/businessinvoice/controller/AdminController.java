package com.managment.businessinvoice.controller;

import com.managment.businessinvoice.dto.LoginUserDto;
import com.managment.businessinvoice.entity.Admin;
import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.response.ResponseHandler;
import com.managment.businessinvoice.service.AdminService;
import com.managment.businessinvoice.service.CustomerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-cookie")
public class AdminController {
  @Autowired
    private AdminService adminService;/*
@Autowired
private JwtHelper jwtHelper;
@GetMapping("/get-user-details")
  public ResponseEntity<UserCredentials> getUser()
  {
    String email= jwtHelper.getUsernameFromToken(AuthController.jwtToken);
    UserCredentials userCredentials=adminService.getAdmin(email);
    return ResponseEntity.ok(userCredentials);
  }*/
}


