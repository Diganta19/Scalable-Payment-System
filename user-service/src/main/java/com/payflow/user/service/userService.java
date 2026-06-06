package com.payflow.user.service;

import com.payflow.user.dto.updateUserRequest;
import com.payflow.user.model.user;
import com.payflow.user.repository.userRepository;
import com.payflow.user.utility.JwtUtility;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userService {
  @Autowired
  private userRepository repo;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  private JwtUtility jwtUtility;

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

  public List<user> getAllUsers() {
    return repo.findAll();
  }

  public Optional<user> getUserById(String userId) {
    Long uId = Long.parseLong(userId);
    return repo.findById(uId);
  }

  public String loginUser(String email, String password) throws Exception {
    user u = repo
      .findByEmail(email)
      .orElseThrow(() -> new Exception("User Not found"));

    if (!passwordEncoder.matches(password, u.getPasswordHash())) {
      throw new Exception("Invalid Password");
    }

    String token = jwtUtility.generateToken(u.getId(), u.getEmail());
    return token;
  }

  public user updateUser(Long userId, updateUserRequest newUser)
    throws Exception {
    user existingUser = repo
      .findById(userId)
      .orElseThrow(() -> new Exception("User Not Found"));

    if (newUser.getName() != null) {
      existingUser.setName(newUser.getName());
    }

    if (newUser.getEmail() != null) {
      existingUser.setEmail(newUser.getEmail());
    }

    if (newUser.getPhone() != null) {
      existingUser.setPhone(newUser.getPhone());
    }

    if (newUser.getPassword() != null && !newUser.getPassword().isBlank()) {
      existingUser.setPasswordHash(
        passwordEncoder.encode(newUser.getPassword())
      );
    }
    user updatedUser = repo.save(existingUser);

    return updatedUser;
  }

  private void validatePassword(String password) throws Exception {
    System.out.println(
      "Password received " + password + " Length " + password.length()
    );
    if (password == null || password.length() < 8) {
      throw new Exception("Password must be at least 8 characters");
    }
    if (!password.matches(".*[A-Z].*")) {
      throw new Exception(
        "Password must contain at least one uppercase letter"
      );
    }
    if (!password.matches(".*[a-z].*")) {
      throw new Exception(
        "Password must contain at least one lowercase letter"
      );
    }
    if (!password.matches(".*[0-9].*")) {
      throw new Exception("Password must contain at least one number");
    }
    if (!password.matches(".*[!@#$%^&*].*")) {
      throw new Exception(
        "Password must contain at least one special character (!@#$%^&*)"
      );
    }
  }

  private void validateEmail(String email) throws Exception {
    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    if (email == null || !email.matches(emailRegex)) {
      throw new Exception("Invalid Email Format");
    }
  }

  private void validatePhone(String phone) throws Exception {
    if (phone == null || !phone.matches("^[0-9]{10}$")) {
      throw new Exception("Phone must be exactly 10 digits");
    }
  }
}
