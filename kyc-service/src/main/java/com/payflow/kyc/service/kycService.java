package com.payflow.kyc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payflow.kyc.dto.kycRequest;
import com.payflow.kyc.dto.kycResponse;
import com.payflow.kyc.enumerated.kycStatus;
import com.payflow.kyc.model.kyc;
import com.payflow.kyc.repository.kycRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class kycService {

    @Autowired
    private kycRepository repo;

    public kycResponse submitKyc(kycRequest kycSubmissionRequest){
        repo.findById(kycSubmissionRequest.getUserId()).ifPresent(existing->{ throw new RuntimeException("KYC already exists for user");});

        kyc application = kyc.builder().userId(kycSubmissionRequest.getUserId()).panNumber(kycSubmissionRequest.getPanNumber()).aadharNumber(kycSubmissionRequest.getAadharNumber())
        .documentUrl(kycSubmissionRequest.getDocumentUrl()).status(kycStatus.PENDING).build();

        kyc saved = repo.save(application);
        return map(saved);
    }

    public kycResponse getKycById(Long userId){
       kyc application = repo.findById(userId).orElseThrow(()-> new RuntimeException("KYC not found"));
       return map(application);
    }

    public List<kycResponse> getPendingKycs(){
        return repo.findByStatus(kycStatus.PENDING)
        .stream()
        .map(this::map)
        .toList();
    }

    public kycResponse approveKyc(Long kycId){
        kyc application = repo.findById(kycId).orElseThrow(()->new RuntimeException("Kyc not found"));
        application.setStatus(kycStatus.APPROVED);
        kyc savedApplication = repo.save(application);
        return map(savedApplication);
    }

    public kycResponse rejectKyc(Long kycId){
        kyc application = repo.findById(kycId).orElseThrow(()-> new RuntimeException("Kyc not found"));
        application.setStatus(kycStatus.REJECTED);
        kyc savedApplication = repo.save(application);
        return map(savedApplication);
    }

    private kycResponse map(
            kyc application
    ) {

        return new kycResponse(
                application.getId(),
                application.getUserId(),
                application.getPanNumber(),
                application.getDocumentUrl(),
                application.getStatus(),
                application.getRejectionReason()
        );
    }
}
