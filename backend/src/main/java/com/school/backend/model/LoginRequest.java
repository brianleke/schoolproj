package com.school.backend.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String firstName;
    private String lastName;
    private Long schoolId;
    private String encryptedPassword;
    private String token;
}
