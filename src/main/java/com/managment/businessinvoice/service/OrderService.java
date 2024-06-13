package com.managment.businessinvoice.service;

import com.managment.businessinvoice.dto.OrderDTO;
import com.managment.businessinvoice.dto.ProductsDto;
import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.entity.Order;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order createOrder(List<ProductsDto> productsDtos, Long id);
    List<Order> getAllOrders();
    List<Order> getAllOrdersByCustomer(Customer customer);
    Optional<Order> getOrderById(Long id);
  //  Order updateOrder(Long id, Order newOrderData);
    boolean deleteOrder(Long id);
}
