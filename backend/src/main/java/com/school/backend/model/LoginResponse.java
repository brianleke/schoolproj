package com.school.backend.model;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String schoolId;
    private String schoolName;
    private String errorResponse;
}
