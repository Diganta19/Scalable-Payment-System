package com.payflow.user.controller;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.payflow.user.dto.loginResponse;
import com.payflow.user.dto.userRequest;
import com.payflow.user.dto.userResponse;
import com.payflow.user.model.user;
import com.payflow.user.repository.userRepository;
import com.payflow.user.service.userService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private userService service;

     @Autowired
    private userRepository repo;
    
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

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        try{
            List<user> allUsers = new ArrayList<>();
            allUsers = service.getAllUsers();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId){
    try{
        Optional<user> userOptional = service.getUserById(userId);
        user u = userOptional.orElseThrow(() -> new Exception("User not found"));
        return new ResponseEntity<>(u, HttpStatus.OK);
    }catch(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody userRequest loginRequest){
        try{
            String token = service.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            user u = repo.findByEmail(loginRequest.getEmail()).get();
            loginResponse response = new loginResponse(token, u.getEmail(), u.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

}
