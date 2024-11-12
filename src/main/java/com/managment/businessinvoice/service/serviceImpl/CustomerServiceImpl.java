package com.managment.businessinvoice.service.serviceImpl;

import com.managment.businessinvoice.Util.AdminUtil;
import com.managment.businessinvoice.dto.CustomerDTO;
import com.managment.businessinvoice.entity.Admin;
import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.exception.CustomerAlreadyExistException;
import com.managment.businessinvoice.repository.AdminRepository;
import com.managment.businessinvoice.repository.CustomerRepository;
import com.managment.businessinvoice.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminUtil adminUtil;

    @Override
    public Customer saveCustomer(Customer customer, HttpServletRequest request) {
        if (customerRepository.getByEmail(customer.getEmail()) != null) {
            throw new CustomerAlreadyExistException("Customer already exist.");
        }
        Admin admin = adminRepository.findById(adminUtil.getAdminId(request)).get();
        customer.setAdmin(admin);
        Customer customersaved = customerRepository.saveAndFlush(customer);
        return customersaved;
    }

    @Override
    public Customer updateCustomer(Long id, Customer customerUpdates) {
        Customer customer = customerRepository.getById(id);
        if (customer == null) {
            throw new CustomerAlreadyExistException("Customer does'nt exist.");
        }
        if (customerUpdates.getEmail() != null) customer.setEmail(customerUpdates.getEmail());
    //    if (customerUpdates.getPassword() != null) customer.setPassword(customerUpdates.getPassword());
        if (customerUpdates.getName() != null) customer.setName(customerUpdates.getName());
        if (customerUpdates.getAddress() != null) customer.setAddress(customerUpdates.getAddress());
        if (customerUpdates.getCNo() != null) customer.setCNo(customerUpdates.getCNo());
        Customer Customersaved = customerRepository.save(customer);
        return Customersaved;
    }

    @Override
    public List<Customer> getCustomer(HttpServletRequest request) {

        List<Customer> customers = customerRepository.findByAdminId(adminUtil.getAdminId(request));
        return customers;
    }
    @Override
    public List<CustomerDTO> getCustomerNameList(HttpServletRequest request) {
        List<Customer> customers = customerRepository.findByAdminId(adminUtil.getAdminId(request));
        List<CustomerDTO>customerDTOS=new ArrayList<>();
        for(Customer customer:customers)
        {
            CustomerDTO customerDTO= modelMapper.map(customer,CustomerDTO.class);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public Customer getByEmail(String email) {
        Customer Customer = customerRepository.getByEmail(email);
        return Customer;
    }

    @Override
    public Customer getById(Long id) {
        Optional<Customer> Customer = customerRepository.findById(id);
        return Customer.get();
    }


    @Override
    @Transactional
    public void removeCustomer(Long id) {
        customerRepository.deleteById(id);
    }

   /* @Override
    public Customer isCustomerExist(Customer Customer) {
        String email = Customer.getEmail();
        Customer Customergetted = customerRepository.findByEmailAndPassword(email, password);
        return Customergetted;
    }*/
    public List<Customer> getCustomerByAdminId(Long id)
    {
        return customerRepository.findByAdminId(id);
    }
}

