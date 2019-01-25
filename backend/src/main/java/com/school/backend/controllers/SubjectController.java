package com.school.backend.controllers;

import com.school.backend.entities.Subject;
import com.school.backend.model.SubjectData;
import com.school.backend.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/subject/{schoolId}/{teacherId}/add")
    @ResponseBody
    Subject addNewSubject (@RequestBody Subject newSubject,
                           @PathVariable Long schoolId,
                           @PathVariable Long teacherId,
                           @RequestParam String token){
        return subjectService.createNewSubject(token, teacherId, schoolId, newSubject);
    }

    @PutMapping("/subject/{subjectId}/{teacherId}/update")
    @ResponseBody
    Subject updateSubject (@PathVariable Long subjectId,
                           @PathVariable Long teacherId,
                           @RequestParam String token) {
        return subjectService.updateSubject(token, teacherId, subjectId);
    }

    @GetMapping("/subject/{schoolId}")
    @ResponseBody
    Map<String, List<SubjectData>> retrieveSubjectsForGrade(@PathVariable Long schoolId,
                                                            @RequestParam String token ){
        return subjectService.retrieveSubjectsForGrade(schoolId, token);
    }
}
