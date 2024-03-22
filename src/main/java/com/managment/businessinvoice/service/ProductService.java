package com.managment.businessinvoice.service;


import com.managment.businessinvoice.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product Product);
    List<Product> getAllProducts();

    Product getProductById(Long id);
    Product updateProduct(Long id, Product newProductData);
    void removeProduct(Long id);
    public Product isProductExist(Long id);
}
