package com.managment.businessinvoice.service;

import com.managment.businessinvoice.dto.CustomerDTO;
import com.managment.businessinvoice.entity.Customer;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer Customer);

    Customer updateCustomer(Long id, Customer Customer);

    List<Customer> getCustomer();
    public List<CustomerDTO> getCustomerNameList();
    Customer getByEmail(String email);

    Customer getById(Long id);

    @Transactional
    void removeCustomer(Long id);

    Customer isCustomerExist(Customer Customer);
}
