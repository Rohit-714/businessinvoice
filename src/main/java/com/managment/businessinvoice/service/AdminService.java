package com.managment.businessinvoice.service;

import com.managment.businessinvoice.entity.Admin;

public interface AdminService {
   Boolean loginCheckPassword(String username,String Password);
   Admin addAdmin(Admin admin);
   Long getAdminId(String username);
}
