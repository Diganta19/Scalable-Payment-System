package com.payflow.user.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.payflow.user.model.user;


@Repository
public interface userRepository extends JpaRepository<user, Long>{
    
}
