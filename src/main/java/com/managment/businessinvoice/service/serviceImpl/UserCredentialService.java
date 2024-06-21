package com.managment.businessinvoice.service.serviceImpl;

import com.managment.businessinvoice.entity.UserCredentials;
import com.managment.businessinvoice.repository.UserCredentialsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialService {
    @Autowired
    private UserCredentialsRepo repository;

    public UserCredentials registerUser(UserCredentials userCredentials) {
        userCredentials.setPassword(userCredentials.getPassword());
        return repository.save(userCredentials);
    }
}
