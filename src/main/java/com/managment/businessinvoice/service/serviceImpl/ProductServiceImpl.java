package com.managment.businessinvoice.service.serviceImpl;


import com.managment.businessinvoice.entity.Product;
import com.managment.businessinvoice.exception.ProductNotFoundException;
import com.managment.businessinvoice.repository.ProductRepository;
import com.managment.businessinvoice.service.ProductService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository ProductRepository;

    @Override
    public Product createProduct(Product Product) {
        Product Productsaved = ProductRepository.saveAndFlush(Product);
        return Productsaved;
    }

    @Override
    public Product updateProduct(Long id, Product ProductUpdates) {
        Product Product = ProductRepository.getById(id);
        if (Product == null) {
            throw new ProductNotFoundException("Product does'nt exist.");
        }
        if (ProductUpdates.getBrand() != null) Product.setBrand(ProductUpdates.getBrand());
        if (ProductUpdates.getPrice() != null) Product.setPrice(ProductUpdates.getPrice());
        if (ProductUpdates.getName() != null) Product.setName(ProductUpdates.getName());
        if (ProductUpdates.getQuantity() != null) Product.setQuantity(ProductUpdates.getQuantity());
        Product Productsaved = ProductRepository.save(Product);
        return Productsaved;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = ProductRepository.findAll();
        return products;
    }


    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = ProductRepository.findById(id);
        return product.get();
    }


    @Override
    @Transactional
    public void removeProduct(Long id) {
        ProductRepository.deleteById(id);
    }

    @Override
    public Product isProductExist(Long id) {
        Optional<com.managment.businessinvoice.entity.Product> productgetted = ProductRepository.findById(id);
        Product product= productgetted.get();
        return product;
    }

}



