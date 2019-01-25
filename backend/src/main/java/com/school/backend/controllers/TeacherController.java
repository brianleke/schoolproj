package com.school.backend.controllers;

import com.school.backend.entities.Teacher;
import com.school.backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/teacher/{schoolId}/add")
    @ResponseBody
    Teacher addNewTeacher (@RequestBody Teacher newTeacher,
                           @PathVariable Long schoolId,
                           @RequestParam String token){
        return teacherService.saveNewTeacher(schoolId, token, newTeacher);
    }

    @DeleteMapping("/teacher/{teacherId}")
    Boolean deleteTeacher(@PathVariable Long teacherId,
                          @RequestParam String token){
        return teacherService.deleteTeacher(teacherId, token);
    }
}
