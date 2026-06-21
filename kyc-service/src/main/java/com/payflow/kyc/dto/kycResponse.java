package com.payflow.kyc.dto;

import com.payflow.kyc.enumerated.kycStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class kycResponse {

     Long id;
     Long userId;
     String aadharNumber;
     String panNumber;
    kycStatus status;
    String rejectionReason;
}
