package com.school.backend.controllers;

import com.school.backend.entities.School;
import com.school.backend.entities.SchoolAdministrators;
import com.school.backend.entities.Teacher;
import com.school.backend.exceptions.GenericNotFoundException;
import com.school.backend.exceptions.InvalidLoginTokenException;
import com.school.backend.exceptions.SchoolNotFoundException;
import com.school.backend.model.SchoolAdmin;
import com.school.backend.model.SchoolData;
import com.school.backend.repository.SchoolAdministratorRepository;
import com.school.backend.repository.SchoolRepository;
import com.school.backend.service.LoginService;
import com.school.backend.service.SchoolAdminService;
import com.school.backend.service.TeacherService;
import com.school.backend.util.EntityToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolAdministratorRepository schoolAdministratorRepository;

    @Autowired
    private SchoolAdminService schoolAdminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/schoolAdmin/{schoolId}")
    @ResponseBody
    SchoolAdministrators newSchoolAdministratorMethod(@RequestBody SchoolAdministrators newSchoolAdministrator,
                                                      @PathVariable Long schoolId,
                                                      @RequestParam String token){
        if(loginService.validateToken(token)) {
            Optional<School> schoolFromId = schoolRepository.findById(schoolId);
            School school;

            if (schoolFromId.isPresent()) {
                school = schoolFromId.get();
                newSchoolAdministrator.setSchool(school);
            } else {
                throw new SchoolNotFoundException(schoolId);
            }

            return schoolAdministratorRepository.save(newSchoolAdministrator);
        }
        else {
            throw new InvalidLoginTokenException();
        }
    }

    @PostMapping("/school")
    School newSchoolMethod(@RequestBody School newSchool,
                           @RequestParam String token){
        if(loginService.validateToken(token)) {
            return schoolRepository.save(newSchool);
        }
        else{
            throw new InvalidLoginTokenException();
        }
    }

    @GetMapping("/school/{schoolId}")
    @ResponseBody
    SchoolData retrieveSchoolMethod(@PathVariable Long schoolId,
                                    @RequestParam String token){
        if(loginService.validateToken(token)) {
            Optional<School> schoolRetrievedOptional = schoolRepository.findById(schoolId);
            if(schoolRetrievedOptional.isPresent()){
                SchoolData schoolData = EntityToDtoMapper.schoolToSchoolDataMapper(schoolRetrievedOptional.get());
                return schoolData;
            }
            else {
                throw new GenericNotFoundException("School does not exist!");
            }
        }
        else{
            throw new InvalidLoginTokenException();
        }
    }

    @GetMapping("/schools")
    List<School> retrieveSchoolLists(@RequestParam String token,
                                     @RequestParam(required = false) String province
                                     ){
        if(loginService.validateToken(token)) {
            return filterSchools(province);
        }
        else{
            throw new InvalidLoginTokenException();
        }
    }

    private List<School> filterSchools(String province){
        Sort sortOrder = new Sort(Sort.Direction.ASC, "name");
        List<School> allSchools = schoolRepository.findAll(sortOrder);
        if(province != null){
            allSchools = allSchools.parallelStream()
                    .filter(school -> school.getProvince().equals(province))
                    .collect(Collectors.toList());
        }

        return allSchools;
    }

    @GetMapping("/schoolAdmins/{schoolId}")
    List<SchoolAdmin> retrieveSchoolAdmins(@PathVariable Long schoolId,
                                           @RequestParam String token){
        if(loginService.validateToken(token)){
            return schoolAdminService.getAllAdminsForSchool(schoolId);
        }
        else {
            throw new InvalidLoginTokenException();
        }
    }

}
