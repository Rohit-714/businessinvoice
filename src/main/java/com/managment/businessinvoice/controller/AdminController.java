package com.managment.businessinvoice.controller;

import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.response.ResponseHandler;
import com.managment.businessinvoice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public ResponseEntity<Object> login()
    {
        System.out.println("okay working");
        Customer customer = customerService.getById(3L);
        return ResponseHandler.generateResponse("customer Created", HttpStatus.OK, customer);
    }
    @GetMapping("/login/page")
    public String loginpage()
    {
        System.out.println("okay nnworking");
        return "home";
    }
}
