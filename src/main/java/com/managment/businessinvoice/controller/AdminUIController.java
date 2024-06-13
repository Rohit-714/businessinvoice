package com.managment.businessinvoice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminUIController {
    @GetMapping("/home")
    public String showHome() {
        return "home"; }
    @GetMapping("/navbar")
    public String showNav() {
        return "navbar"; }
    @GetMapping("/orders")
    public String showOrdersPage() {
        return "orders"; }
    @GetMapping("/products")
    public String showProductPage() {
        return "products"; }
    @GetMapping("/product/update/{id}")
    public String updateProduct(@PathVariable int id, Model model) {
        model.addAttribute("productId", id);
        return "updateProduct";
    }
    @GetMapping("add/customer")
    public String addCustomer()
    {
        return "addCustomer";
    }
    @GetMapping("/customer/update/{id}")
    public String updateCustomer(@PathVariable int id, Model model)
    {
        model.addAttribute("customerId", id);
        return "updateCustomerDetails";
    }
    @GetMapping("/customers")
    public String showCustomerPage() {
        return "customers"; }
    @GetMapping("/buy-products")
    public String buyProduct(@RequestParam("customerId") Long customerId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("customerId", customerId);
        return "redirect:buyproduct";
    }

    @GetMapping("/add/products")
    public String addProduct() {
        return "addProduct";
    }
}
