package com.managment.businessinvoice.controller;

import com.managment.businessinvoice.entity.Admin;
import com.managment.businessinvoice.entity.JwtRequest;
import com.managment.businessinvoice.entity.JwtResponse;
import com.managment.businessinvoice.exception.AdminAlreadyExistException;
import com.managment.businessinvoice.security.JwtHelper;
import com.managment.businessinvoice.service.AdminService;
import com.managment.businessinvoice.service.serviceImpl.UserCredentialService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserCredentialService userCredentialService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;


    public static String jwtToken;
    @Autowired
    private AdminService adminService;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody JwtRequest request) {
        Authentication authentication = this.doAuthenticate(request.getUsername(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        jwtToken = this.helper.generateToken(authentication);
        Long adminId = adminService.getAdminId(request.getUsername());
        return ResponseEntity.ok(adminId);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Admin user)
    {
        if(user!=null) {
            try {
                Admin userSaved = adminService.addAdmin(user);
                Authentication authentication = this.doAuthenticate(userSaved.getUsername(), userSaved.getPassword());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userSaved.getUsername());
                JwtResponse response = JwtResponse.builder().jwtToken(this.helper.generateToken(authentication)).username(userDetails.getUsername()).build();
                return ResponseEntity.ok(response);
            } catch (AdminAlreadyExistException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already in use.");
            }
        }
            return generateRespose("User unable to create", HttpStatus.BAD_REQUEST, user);
    }
    @PostMapping("/validate/token")
    public ResponseEntity <Object> validate(@RequestParam("token") String token)
    {
     String username = helper.getUsernameFromToken(token);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if(this.helper.validateToken(token,userDetails))
            return generateRespose("Validated",HttpStatus.OK,userDetails);
        return generateRespose("invalid",HttpStatus.FORBIDDEN,null);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("logout");
        if (jwtToken != null) {
            jwtToken = null;
            response.sendRedirect("/admin/loginPage");
            return "Logged out successfully";
        }
        return "Authorization header missing or invalid";
    }

    private Authentication doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
         return manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

        public ResponseEntity<Object> generateRespose(String message, HttpStatus st, Object responseobj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("meaasge", message);
        map.put("Status", st.value());
        map.put("data", responseobj);

        return new ResponseEntity<Object>(map, st);
    }
}
