package com.managment.businessinvoice.service.serviceImpl;

import com.managment.businessinvoice.entity.Admin;
import com.managment.businessinvoice.exception.AdminAlreadyExistException;
import com.managment.businessinvoice.exception.AdminNotFoundException;
import com.managment.businessinvoice.repository.AdminRepository;
import com.managment.businessinvoice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

@Autowired
    AdminRepository adminRepository;
    public Boolean loginCheckPassword(String username,String password)
    {
        Admin admin=adminRepository.findByUsernameAndPassword(username,password);
        if(admin!=null)
            return true;
        return false;
    }
    public Admin addAdmin(Admin admin)
    {
        if(adminRepository.findByUsername(admin.getUsername()).isPresent())
            throw new AdminAlreadyExistException();
        Admin adminSaved=adminRepository.save(admin);
        return adminSaved;
    }
    public Long getAdminId(String username){
        Optional<Admin> adminOptional=adminRepository.findByUsername(username);
        if(adminOptional.isPresent())
            return adminOptional.get().getId();
        throw new AdminNotFoundException("No Admin present with Username : "+username);
    }
}
