package com.payflow.kyc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payflow.kyc.enumerated.kycStatus;
import com.payflow.kyc.model.kyc;

public interface kycRepository extends JpaRepository<kyc, Long> {

    Optional<kyc> findById(Long userId);
    List<kyc> findByStatus(kycStatus status);
    
}
