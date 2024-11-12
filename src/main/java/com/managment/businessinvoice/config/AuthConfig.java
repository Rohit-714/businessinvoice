package com.managment.businessinvoice.config;

import com.managment.businessinvoice.entity.Admin;
import com.managment.businessinvoice.entity.UserCredentials;
import com.managment.businessinvoice.repository.AdminRepository;
import com.managment.businessinvoice.repository.UserCredentialsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AuthConfig {
    private final DataSource dataSource;
    @Autowired
    private AdminRepository adminRepository;

    public AuthConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserDetailsService userCredentialsService() {
        List<UserDetails> users = adminRepository.findAll()
                .stream()
                .map(this::mapToUserDetails)
                .collect(Collectors.toList());
        return new InMemoryUserDetailsManager(users);
    }

    private UserDetails mapToUserDetails(Admin user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder().encode(user.getPassword()))
                .roles("ADMIN")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}