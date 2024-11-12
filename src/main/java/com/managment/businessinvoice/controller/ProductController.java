package com.managment.businessinvoice.controller;

import com.managment.businessinvoice.entity.Product;
import com.managment.businessinvoice.response.ResponseHandler;
import com.managment.businessinvoice.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Object> addProduct(@RequestBody Product Product, HttpServletRequest request) {
        try {
            Product savedProduct = productService.createProduct(Product,request);
            return ResponseHandler.generateResponse("Product Created", HttpStatus.OK, savedProduct);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, Product);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> checkProduct(@PathVariable Long id) {
        try {
            Product Productgetted = productService.isProductExist(id);
            return ResponseHandler.generateResponse("login successfull", HttpStatus.OK, Productgetted);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Invalid credentials", HttpStatus.UNPROCESSABLE_ENTITY, null);
        }
    }
    @GetMapping
    public ResponseEntity<List<Product>> getProducts(HttpServletRequest request) {
        try {
            List<Product> products = productService.getAllProducts(request);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable Long id) {
        try {
            System.out.println("hhggg");
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("unable to find Product", HttpStatus.UNPROCESSABLE_ENTITY, null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        try {
            productService.removeProduct(id);
            return ResponseHandler.generateResponse("Product deleted by id", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Product can't delete", HttpStatus.UNPROCESSABLE_ENTITY, null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id,@RequestBody Product Product) {
        try {
            Product savedProduct = productService.updateProduct(id,Product);
            return ResponseHandler.generateResponse("Product Created", HttpStatus.OK, savedProduct);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Product unable to create", HttpStatus.UNPROCESSABLE_ENTITY, null);
        }
    }

    @GetMapping("/best-performing-products")
    public ResponseEntity<Object> bestPerformingProduct(HttpServletRequest request) {
        try {
            List<Product> listProduct = productService.getBestProducts(request);
            return ResponseEntity.ok(listProduct);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Unable to fetch list", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/worst-performing-products")
    public ResponseEntity<Object> worstPerformingProduct(HttpServletRequest request) {
        try {
            List<Product> listProduct = productService.getWorstProducts(request);
            return ResponseEntity.ok(listProduct);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Unable to fetch list", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    @GetMapping("/empty-stock-products")
    public ResponseEntity<Object> emptyStockProduct(HttpServletRequest request) {
        try {
            List<Product> listProduct = productService.getEmptyStockProduct(request);
            return ResponseEntity.ok(listProduct);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Unable to fetch list", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
