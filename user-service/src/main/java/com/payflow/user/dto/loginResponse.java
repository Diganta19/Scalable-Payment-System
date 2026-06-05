package com.payflow.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class loginResponse {
    private String token;
    private String email;
    private Long userId;
}
