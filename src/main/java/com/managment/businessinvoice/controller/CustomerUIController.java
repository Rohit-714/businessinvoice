package com.managment.businessinvoice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerUIController {
    @GetMapping("/home")
    public String showOrdersPage() {
        return "customerHome";
    }
    @GetMapping("/buy-product")
    public String buyProduct() {
        return "buyproduct";
    }

}






