package com.managment.businessinvoice.service.serviceImpl;

import com.managment.businessinvoice.entity.Admin;
import com.managment.businessinvoice.repository.AdminRepository;
import com.managment.businessinvoice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;

    public Boolean loginCheckPassword(String username,String password)
    {
        Admin admin=adminRepository.findByUserNameAndPassword(username,password);
        if(admin!=null)
            return true;
        return false;
    }
    public Admin addAdmin(Admin admin)
    {
        Admin adminSaved=adminRepository.save(admin);
        return adminSaved;
    }
}
