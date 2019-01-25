package com.school.backend.controllers;

import com.school.backend.model.LoginRequest;
import com.school.backend.model.LoginResponse;
import com.school.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String encryptedPassword = loginRequest.getEncryptedPassword();
        LoginResponse loginResponseForUser = loginService.getLoginResponseForUser(username, encryptedPassword);
        return new ResponseEntity<>(loginResponseForUser, HttpStatus.OK);
    }
}
