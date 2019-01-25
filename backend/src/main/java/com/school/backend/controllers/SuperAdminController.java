package com.school.backend.controllers;

import com.school.backend.entities.SuperAdmin;
import com.school.backend.model.LoginRequest;
import com.school.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuperAdminController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/super-admin/add")
    @ResponseBody
    public SuperAdmin saveNewSuperAdmin(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String encryptedPassword = loginRequest.getEncryptedPassword();
        String token = loginRequest.getToken();
        String firstName = loginRequest.getFirstName();
        String lastName = loginRequest.getLastName();
        return loginService.saveNewSuperAdmin(username, encryptedPassword, token, firstName, lastName);
    }
}
