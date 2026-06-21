package com.payflow.kyc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payflow.kyc.dto.kycRequest;
import com.payflow.kyc.dto.kycResponse;
import com.payflow.kyc.service.kycService;



@RestController
@RequestMapping("/api/kyc")
public class kycController {

    @Autowired
    private  kycService service;

    @PostMapping
    public ResponseEntity<kycResponse> submitKyc(@RequestBody kycRequest kycSubmissionRequest){
        return  ResponseEntity.status(HttpStatus.CREATED).body(service.submitKyc(kycSubmissionRequest));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<kycResponse> getKycById(@PathVariable Long userId){
            return new ResponseEntity<>(service.getKycById(userId), HttpStatus.OK);
    }

    @GetMapping("/admin/kyc/pending")
    public ResponseEntity<List<kycResponse>> getPendingKycs(){
        return new ResponseEntity<>(service.getPendingKycs(), HttpStatus.OK);
    }

    @PutMapping("/admin/{id}/approve")
    public ResponseEntity<kycResponse> approveKyc(@PathVariable Long id){
       kycResponse approvedKyc = service.approveKyc(id);
       return new ResponseEntity<>(approvedKyc, HttpStatus.OK);
    }

    @PutMapping("/admin/{id}/reject")
    public ResponseEntity<kycResponse> rejectKyc(@PathVariable Long id){
        kycResponse rejectedKyc = service.rejectKyc(id);
        return new ResponseEntity<>(rejectedKyc, HttpStatus.OK);
    }



}
