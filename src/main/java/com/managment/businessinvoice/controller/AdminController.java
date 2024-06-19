package com.managment.businessinvoice.controller;

import com.managment.businessinvoice.dto.LoginUserDto;
import com.managment.businessinvoice.entity.Admin;
import com.managment.businessinvoice.entity.Customer;
import com.managment.businessinvoice.response.ResponseHandler;
import com.managment.businessinvoice.service.AdminService;
import com.managment.businessinvoice.service.CustomerService;
import com.managment.businessinvoice.service.serviceImpl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getUserName(), loginUserDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Admin admin)
    {
        return ResponseHandler.generateResponse("customer Created", HttpStatus.OK,
                adminService.addAdmin(admin));
    }
}
