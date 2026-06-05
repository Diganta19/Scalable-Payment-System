package com.payflow.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.payflow.user.model.user;
import com.payflow.user.repository.userRepository;

@Service
public class userService {
    
    @Autowired
    private userRepository repo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public user addUser(user u) throws Exception {
        //custom validations
        validatePassword(u.getPasswordHash());
        validateEmail(u.getEmail());
        validatePhone(u.getPhone());

        //HashPassword
        u.setPasswordHash(passwordEncoder.encode(u.getPasswordHash()));
        
        //save
        return repo.save(u);
    }

    private void validatePassword(String password) throws Exception {
        System.out.println("Password received "+ password + " Length "+ password.length());
        if (password == null || password.length() < 8) {
            throw new Exception("Password must be at least 8 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new Exception("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new Exception("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new Exception("Password must contain at least one number");
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            throw new Exception("Password must contain at least one special character (!@#$%^&*)");
        }
    }

    private void validateEmail(String email) throws Exception{
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if(email == null || !email.matches(emailRegex)){
            throw new Exception("Invalid Email Format");
        }
    }

     private void validatePhone(String phone) throws Exception {
        if (phone == null || !phone.matches("^[0-9]{10}$")) {
            throw new Exception("Phone must be exactly 10 digits");
        }
    }
}
