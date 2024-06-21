package com.managment.businessinvoice.config;

import com.managment.businessinvoice.entity.JwtRequest;
import com.managment.businessinvoice.entity.JwtResponse;
import com.managment.businessinvoice.entity.UserCredentials;
import com.managment.businessinvoice.security.JwtHelper;
import com.managment.businessinvoice.service.serviceImpl.UserCredentialService;
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

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody JwtRequest request) {
        Authentication authentication= this.doAuthenticate(request.getEmail(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        jwtToken = this.helper.generateToken(authentication);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(jwtToken)
                .username(userDetails.getUsername()).build();
        return  ResponseEntity.ok().build();
    }
    @PostMapping("/register")
    public ResponseEntity <Object> register(@RequestBody UserCredentials user)
    {
        if(user!=null) {
            userCredentialService.registerUser(user);
            return generateRespose("User Created", HttpStatus.OK, user);
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
