package com.payflow.user.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.payflow.user.model.user;


@Repository
public interface userRepository extends JpaRepository<user, Long>{
    Optional<user> findByEmail(String email);
}
