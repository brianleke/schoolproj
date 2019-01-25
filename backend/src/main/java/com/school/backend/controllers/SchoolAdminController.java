package com.school.backend.controllers;

import com.school.backend.entities.SchoolAdministrators;
import com.school.backend.entities.SuperAdmin;
import com.school.backend.model.LoginRequest;
import com.school.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolAdminController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/school-admin/add")
    public SchoolAdministrators saveNewSchoolAdmin(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String encryptedPassword = loginRequest.getEncryptedPassword();
        String token = loginRequest.getToken();
        String firstName = loginRequest.getFirstName();
        String lastName = loginRequest.getLastName();
        Long schoolId = loginRequest.getSchoolId();
        return loginService.saveNewSchoolAdmin(username, encryptedPassword, token, firstName, lastName, schoolId);
    }
}
