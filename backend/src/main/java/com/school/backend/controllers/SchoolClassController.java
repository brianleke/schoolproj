package com.school.backend.controllers;

import com.school.backend.entities.Classes;
import com.school.backend.entities.School;
import com.school.backend.exceptions.GenericNotFoundException;
import com.school.backend.exceptions.InvalidLoginTokenException;
import com.school.backend.exceptions.SchoolNotFoundException;
import com.school.backend.repository.ClassesRepository;
import com.school.backend.repository.SchoolRepository;
import com.school.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SchoolClassController {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private LoginService loginService;

    @PostMapping("/classes/{schoolId}/add")
    @ResponseBody
    Classes addNewTeacher (@RequestBody Classes newClasses,
                           @PathVariable Long schoolId,
                           @RequestParam String token){

        if(loginService.validateToken(token)) {
            Optional<School> schoolFromId = schoolRepository.findById(schoolId);
            School school;

            if (schoolFromId.isPresent()) {
                school = schoolFromId.get();
                newClasses.setSchool(school);

            } else {
                throw new SchoolNotFoundException(schoolId);
            }

        }
        else {
            throw new InvalidLoginTokenException();
        }

        try {
            return classesRepository.save(newClasses);
        }
        catch (Exception exception){
            throw new GenericNotFoundException("Something went wrong.");
        }
    }
}
