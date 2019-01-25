package com.school.backend.controllers;

import com.school.backend.entities.Classes;
import com.school.backend.entities.Results;
import com.school.backend.entities.School;
import com.school.backend.entities.Student;
import com.school.backend.exceptions.GenericNotFoundException;
import com.school.backend.exceptions.InvalidLoginTokenException;
import com.school.backend.exceptions.SchoolNotFoundException;
import com.school.backend.repository.ClassesRepository;
import com.school.backend.repository.SchoolRepository;
import com.school.backend.repository.StudentRepository;
import com.school.backend.service.LoginService;
import com.school.backend.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ResultsService resultsService;

    @PostMapping("/student/{schoolId}/{classId}/add")
    @ResponseBody
    Student newSchoolStudentMethod(@RequestBody Student newStudent,
                                   @PathVariable Long schoolId,
                                   @PathVariable Long classId,
                                   @RequestParam String token){
        if(loginService.validateToken(token)) {
            Optional<School> schoolFromId = schoolRepository.findById(schoolId);
            School school;

            if (schoolFromId.isPresent()) {
                school = schoolFromId.get();
                newStudent.setSchool(school);
                Optional<Classes> classById = classesRepository.findById(classId);
                classById.ifPresent(newStudent::setStudentClass);
            } else {
                throw new SchoolNotFoundException(schoolId);
            }

            try {
                Student student = studentRepository.save(newStudent);
                return student;
            }
            catch (Exception exception){
                throw new GenericNotFoundException("No class Found for student.");
            }
        }
        else {
            throw new InvalidLoginTokenException();
        }
    }

    @PostMapping("/student/{studentId}/results/save")
    Boolean saveStudentResults(@RequestBody Results studentResults,
                               @RequestParam String token,
                               @PathVariable Long studentId){
        return true;

    }
}
