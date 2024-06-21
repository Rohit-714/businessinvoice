package com.managment.businessinvoice.service.serviceImpl;
import com.managment.businessinvoice.entity.Admin;
import com.managment.businessinvoice.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/*
@Service

public class CustomUserDetailsService implements UserDetailsService {

    // Example repository
     @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /* Admin user = adminRepository.findByUserName(username);
        if (user == null) {
           throw new UsernameNotFoundException("User not found");
         }
         return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());

        // Example with hardcoded user
        return User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
    }
}
*/