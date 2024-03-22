package com.managment.businessinvoice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class UIController {
    @GetMapping("/orders")
    public String showOrdersPage() {
        return "orders"; // Assuming "orders.html" is the name of your HTML file
    }
}
