package com.payflow.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class updateUserRequest {

    private String name;
    private String email;
    private String phone;
    private String password;
}
