package com.payflow.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.payflow.user.dto.userResponse;
import com.payflow.user.model.user;
import com.payflow.user.service.userService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private userService service;
    
    @PostMapping("/create-user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody user u){
        try{
            user u1 = service.addUser(u);
            userResponse response = userResponse.builder()
            .userId(u1.getId())
            .email(u1.getEmail())
            .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
