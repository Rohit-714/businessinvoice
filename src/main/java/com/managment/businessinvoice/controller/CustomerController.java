package com.managment.businessinvoice.controller;

import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.response.ResponseHandler;
import com.managment.businessinvoice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Object> addcustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            return ResponseHandler.generateResponse("customer Created", HttpStatus.OK, savedCustomer);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, customer);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> checkcustomer(@RequestBody Customer customer) {
        try {
            Customer customergetted = customerService.isCustomerExist(customer);
            return ResponseHandler.generateResponse("login successfull", HttpStatus.OK, customergetted);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Invalid credentials", HttpStatus.UNPROCESSABLE_ENTITY, null);
        }
    }
    @GetMapping
    public ResponseEntity<Object> getcustomer() {
        try {
            List<Customer> customers = customerService.getCustomer();
            return ResponseHandler.generateResponse("customers are listed", HttpStatus.OK, customers);
        } catch (Exception e) {

            return ResponseHandler.generateResponse("unable to find customers", HttpStatus.UNPROCESSABLE_ENTITY, null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getcustomer(@PathVariable Long id) {
        try {
            Customer customer = customerService.getById(id);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("unable to find customer", HttpStatus.UNPROCESSABLE_ENTITY, null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletecustomer(@PathVariable Long id) {
        try {
            customerService.removeCustomer(id);
            return ResponseHandler.generateResponse("customer deleted by id", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("customer can't delete", HttpStatus.UNPROCESSABLE_ENTITY, null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatecustomer(@PathVariable Long id,@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.updateCustomer(id,customer);
            return ResponseHandler.generateResponse("customer Created", HttpStatus.OK, savedCustomer);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("customer unable to create", HttpStatus.UNPROCESSABLE_ENTITY, null);
        }
    }
}
