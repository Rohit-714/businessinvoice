package com.managment.businessinvoice.service.serviceImpl;


import com.managment.businessinvoice.dto.OrderDTO;
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

    @Override
    public Order createOrder(OrderDTO order, Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            order.setCustomer(customer);
            List<Long> ids = order.getProductId();
            List<Product> products = new ArrayList<>();
            List<Integer> quantities = order.getQuantity();
            System.out.println("Quantity = " + order.getQuantity());
            System.out.println("Product = " + ids);

            for (int i = 0; i < ids.size(); i++) {
                Optional<Product> p = productRepository.findById(ids.get(i));
                products.add(p.get());
            }

            Long sum = 0L;
            int i = 0;
            for (Product p : products) {

                p.setQuantity(quantities.get(i));
                products.get(i).setQuantity(quantities.get(i));
                i++;
                sum = sum + p.getPrice() * p.getQuantity();
            }
            order.setTotalAmount(sum);
            order.setProducts(products);
            order.setInvoiceDate(new Date());
            Order ordersaved = modelMapper.map(order, Order.class);

            Invoice invoice = new Invoice();

            invoice.setTotalAmount(BigDecimal.valueOf(sum));
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
