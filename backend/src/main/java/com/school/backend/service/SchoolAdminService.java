package com.school.backend.service;

import com.school.backend.entities.School;
import com.school.backend.entities.SchoolAdministrators;
import com.school.backend.model.SchoolAdmin;
import com.school.backend.repository.SchoolAdministratorRepository;
import com.school.backend.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchoolAdminService {
    @Autowired
    private SchoolAdministratorRepository schoolAdministratorRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public List<SchoolAdmin> getAllAdminsForSchool(Long schoolId){
        List<SchoolAdmin> schoolAdmins = new ArrayList<>();
        Optional<School> schoolRetrieved = schoolRepository.findById(schoolId);

        if(schoolRetrieved.isPresent()) {
            List<SchoolAdministrators> allSchoolAdmins = schoolAdministratorRepository.findBySchool(schoolRetrieved.get());

            schoolAdmins = allSchoolAdmins.stream().map(schoolAdmin -> {
                SchoolAdmin modifiedSchoolAdmin = new SchoolAdmin();
                modifiedSchoolAdmin.setId(schoolAdmin.getId());
                modifiedSchoolAdmin.setFirstName(schoolAdmin.getFirstName());
                modifiedSchoolAdmin.setLastName(schoolAdmin.getLastName());
                modifiedSchoolAdmin.setUsername(schoolAdmin.getUsername());

                return modifiedSchoolAdmin;
            }).collect(Collectors.toList());
        }

//        allSchoolAdmins.forEach(schoolAdmin -> {
//            SchoolAdmin modifiedSchoolAdmin = new SchoolAdmin();
//            modifiedSchoolAdmin.setId(schoolAdmin.getId());
//            modifiedSchoolAdmin.setFirstName(schoolAdmin.getFirstName());
//            modifiedSchoolAdmin.setLastName(schoolAdmin.getLastName());
//            modifiedSchoolAdmin.setUsername(schoolAdmin.getUsername());
//
//            schoolAdmins.add(modifiedSchoolAdmin);
//        });

        return schoolAdmins;
    }
}
