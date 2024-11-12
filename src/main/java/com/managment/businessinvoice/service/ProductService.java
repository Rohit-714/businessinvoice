package com.managment.businessinvoice.service;


import com.managment.businessinvoice.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product Product, HttpServletRequest request);
    List<Product> getAllProducts(HttpServletRequest request);
    public Product setBuyedQuantity(Long id, Integer quantity);
    Product getProductById(Long id);
    Product updateProduct(Long id, Product newProductData);
    void removeProduct(Long id);
    public Product isProductExist(Long id);
    public List<Product> getBestProducts(HttpServletRequest request);
    public List<Product> getWorstProducts(HttpServletRequest request);
    public List<Product>  getEmptyStockProduct(HttpServletRequest request);
}
