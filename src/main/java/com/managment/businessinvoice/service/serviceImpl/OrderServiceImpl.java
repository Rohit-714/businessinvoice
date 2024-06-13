package com.managment.businessinvoice.service.serviceImpl;


import com.managment.businessinvoice.dto.OrderDTO;
import com.managment.businessinvoice.dto.ProductsDto;
import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.entity.Invoice;
import com.managment.businessinvoice.entity.Order;
import com.managment.businessinvoice.entity.Product;
import com.managment.businessinvoice.exception.CustomerNotFoundException;
import com.managment.businessinvoice.repository.CustomerRepository;
import com.managment.businessinvoice.repository.InvoiceRepository;
import com.managment.businessinvoice.repository.OrderRepository;
import com.managment.businessinvoice.repository.ProductRepository;
import com.managment.businessinvoice.service.OrderService;
import com.managment.businessinvoice.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
@Autowired
private ProductService productService;
    @Override
    public Order createOrder(List<ProductsDto> productsDto, Long customerId) {
        OrderDTO order=new OrderDTO();
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            order.setCustomer(customer);
            List<Product> products = new ArrayList<>();

            for (int i = 0; i < productsDto.size(); i++) {
                ProductsDto productDto=productsDto.get(i);
                Product product=modelMapper.map(productDto,Product.class);
                product.setBuyedQuantity(productDto.getQuantity());
                productService.setBuyedQuantity(product.getId(),productDto.getQuantity());
                products.add(product);
            }
            Long sum = 0L;
            int i = 0;

            for (Product p : products) {
               sum = sum+(p.getPrice()*p.getQuantity());
            }
            order.setTotalAmount(sum);
            order.setProducts(products);
            order.setInvoiceDate(new Date());
            Order ordersaved = modelMapper.map(order, Order.class);
            Invoice invoice = new Invoice();
            invoice.setTotalAmount(sum);
            invoice.setCustomer(customer);
            List<Order> orders = new ArrayList<>();
            orders.add(ordersaved);
            invoice.setOrders(orders);
            invoiceRepository.saveAndFlush(invoice);
            ordersaved.setInvoice(invoice);
            return orderRepository.save(ordersaved);
        }
        throw new CustomerNotFoundException();
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }
    @Override
    public List<Order> getAllOrdersByCustomer(Customer customer) {
        Customer customera=customerRepository.findById(2L).get();
        List<Order> orders = orderRepository.findByCustomer(customera);
        return orders;
    }
    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }


    @Override
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
