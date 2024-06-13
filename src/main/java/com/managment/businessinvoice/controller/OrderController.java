package com.managment.businessinvoice.controller;

import com.managment.businessinvoice.dto.OrderDTO;
import com.managment.businessinvoice.dto.ProductsDto;
import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.entity.Order;
import com.managment.businessinvoice.response.ResponseHandler;
import com.managment.businessinvoice.service.InvoiceService;
import com.managment.businessinvoice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private InvoiceService invoiceService;
@Autowired private OrderService orderService;

  /*  @PostMapping("{id}")
    public ResponseEntity<Object> createOrder(@RequestBody OrderDTO Order, @PathVariable Long id) {
        Order createdOrder = orderService.createOrder(Order,id);
        return ResponseHandler.generateResponse("Order created successfully", HttpStatus.CREATED, createdOrder);
    }*/
    @PostMapping("/create/{id}")
    public ResponseEntity<Object> createOrder(@RequestBody List<ProductsDto> productsDtos, @PathVariable Long id) {
        Order createdOrder = orderService.createOrder(productsDtos,id);
        return ResponseHandler.generateResponse("Order created successfully", HttpStatus.CREATED, createdOrder);
    }
    @GetMapping("customer")
    public ResponseEntity<Object> getAllOrdersByCustomerId() {
        Customer customer=new Customer();
        List<Order> orders = orderService.getAllOrdersByCustomer(customer);
        return ResponseHandler.generateResponse("All Order retrieved successfully", HttpStatus.OK, orders);
    }
    @GetMapping
    public ResponseEntity<Object> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseHandler.generateResponse("All Order retrieved successfully", HttpStatus.OK, orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            return ResponseHandler.generateResponse("Order retrieved successfully", HttpStatus.OK, order.get());
        } else {
            return ResponseHandler.generateResponse("Order not found", HttpStatus.NOT_FOUND, null);
        }
    }

  /*  @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody Order newOrderData) {
        Order updatedOrder = orderService.updateOrder(id, newOrderData);
        if (updatedOrder != null) {
            return ResponseHandler.generateResponse("Order updated successfully", HttpStatus.OK, updatedOrder);
        } else {
            return ResponseHandler.generateResponse("Order not found", HttpStatus.NOT_FOUND, null);
        }
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (deleted) {
            return ResponseHandler.generateResponse("Order deleted successfully", HttpStatus.NO_CONTENT, null);
        } else {
            return ResponseHandler.generateResponse("Order not found", HttpStatus.NOT_FOUND, null);
        }
    }
}
