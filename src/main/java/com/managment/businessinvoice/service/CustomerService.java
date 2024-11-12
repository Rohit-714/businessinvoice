package com.managment.businessinvoice.service;

import com.managment.businessinvoice.dto.CustomerDTO;
import com.managment.businessinvoice.entity.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer Customer, HttpServletRequest httpServletRequest);

    Customer updateCustomer(Long id, Customer Customer);

    List<Customer> getCustomer(HttpServletRequest request);
    public List<CustomerDTO> getCustomerNameList(HttpServletRequest request);
    Customer getByEmail(String email);

    Customer getById(Long id);

    @Transactional
    void removeCustomer(Long id);

  //  Customer isCustomerExist(Customer Customer);
    public List<Customer> getCustomerByAdminId(Long id);
}
