package com.managment.businessinvoice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
public class CustomerUIController {
    @GetMapping("/home")
    public String showOrdersPage() {
        return "customerHome";
    }
    @GetMapping("/buy-products")
    public String buyProduct(@RequestParam("customerId") Long customerId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("customerId", customerId);
        return "buyproduct";
    }
    @GetMapping("order")
    public String customerOrder() {
        return "customerorder";
    }

}






