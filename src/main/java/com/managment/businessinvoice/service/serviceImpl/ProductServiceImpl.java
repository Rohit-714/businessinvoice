package com.managment.businessinvoice.service.serviceImpl;

import com.managment.businessinvoice.Util.AdminUtil;
import com.managment.businessinvoice.entity.Admin;
import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.entity.Product;
import com.managment.businessinvoice.exception.ProductNotFoundException;
import com.managment.businessinvoice.repository.AdminRepository;
import com.managment.businessinvoice.repository.ProductRepository;
import com.managment.businessinvoice.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminUtil adminUtil;
    @Override
    public Product createProduct(Product product, HttpServletRequest request) {
        Optional<Admin> admin=adminRepository.findById(adminUtil.getAdminId(request));
        product.setAdmin(admin.get());

        Product productsaved = productRepository.saveAndFlush(product);
        return productsaved;
    }

    @Override
    public Product updateProduct(Long id, Product ProductUpdates) {
        Product product = productRepository.getById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product does'nt exist.");
        }
        if (ProductUpdates.getBrand() != null) product.setBrand(ProductUpdates.getBrand());
        product.setPrice(ProductUpdates.getPrice());
        if (ProductUpdates.getName() != null) product.setName(ProductUpdates.getName());
        product.setQuantity(ProductUpdates.getQuantity());
        Product productsaved = productRepository.save(product);
        return productsaved;
    }
    @Override
    public Product setBuyedQuantity(Long id, Integer quantity) {
        Product product = productRepository.getById(id);
        product.setBuyedQuantity(quantity);
        Product productsaved = productRepository.save(product);
        return productsaved;
    }
    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }


    @Override
    @Transactional
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product isProductExist(Long id) {
        Optional<com.managment.businessinvoice.entity.Product> productgetted = productRepository.findById(id);
        Product product= productgetted.get();
        return product;
    }
    @Override
    public List<Product> getAllProducts(HttpServletRequest request) {
        List<Product> products = productRepository.findByAdminId(adminUtil.getAdminId(request));
        return products;
    }
    @Override
    public List<Product> getBestProducts(HttpServletRequest request) {
        List<Product> products = getAllProducts(request);
        products.sort(Comparator.comparingInt(Product::getBuyedQuantity).reversed());
        return products;
    }
    @Override
    public List<Product> getWorstProducts(HttpServletRequest request) {
        List<Product> products = getAllProducts(request);
        products.sort(Comparator.comparingInt(Product::getBuyedQuantity));
        return products;
    }
    @Override
    public List<Product> getEmptyStockProduct(HttpServletRequest request) {
        List<Product> products = getAllProducts(request);
        List<Product> emptyStock=products.stream().filter(product -> product.getQuantity()==0).collect(Collectors.toList());
        return emptyStock;
    }
}



