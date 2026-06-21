package com.payflow.kyc.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.payflow.kyc.enumerated.kycStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kyc_applications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class kyc {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =  false)
    private Long userId;

    @Column(nullable = false)
    private String panNumber;


    @Column(nullable = false)
    private String aadharNumber;
    
    private String documentUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private kycStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private String rejectionReason;
}
