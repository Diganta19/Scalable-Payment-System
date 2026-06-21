package com.payflow.kyc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class kycRequest {
    Long userId;
    String panNumber;
    @JsonProperty("aadhaarNumber")
    String aadharNumber;
    String documentUrl;
   
}
