package com.school.backend.controllers;

import com.school.backend.entities.Marks;
import com.school.backend.entities.Results;
import com.school.backend.entities.Student;
import com.school.backend.entities.Subject;
import com.school.backend.exceptions.GenericNotFoundException;
import com.school.backend.model.StudentData;
import com.school.backend.repository.*;
import com.school.backend.service.LoginService;
import com.school.backend.service.ResultsService;
import com.school.backend.util.EntityToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ResultsController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ResultsRepository resultsRepository;

    @Autowired
    private ResultsService resultsService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/results/{studentId}/")
    StudentData saveStudentResultsForTerm(@PathVariable Long studentId,
                                          @RequestBody Results results){
        return resultsService.saveOrUpdateResultsForStudent(studentId, results);
    }
}
