package com.school.backend.service;

import com.school.backend.entities.School;
import com.school.backend.entities.Teacher;
import com.school.backend.exceptions.InvalidLoginTokenException;
import com.school.backend.exceptions.SchoolNotFoundException;
import com.school.backend.repository.SchoolRepository;
import com.school.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private LoginService loginService;

    public Teacher saveNewTeacher(Long schoolId, String token, Teacher newTeacher){
        if(loginService.validateToken(token)) {
            Optional<School> schoolFromId = schoolRepository.findById(schoolId);

            if (schoolFromId.isPresent()) {
                School school = schoolFromId.get();
                newTeacher.setSchool(school);
            } else {
                throw new SchoolNotFoundException(schoolId);
            }

            return teacherRepository.save(newTeacher);
        }
        else {
            throw new InvalidLoginTokenException();
        }
    }

    public Boolean deleteTeacher(Long teacherId, String token){
        if(loginService.validateToken(token)) {
            boolean teacherExists = teacherRepository.existsById(teacherId);
            if (teacherExists) {
                teacherRepository.deleteById(teacherId);
            }

            return teacherExists;
        }
        else {
            throw new InvalidLoginTokenException();
        }
    }

    public List<Teacher> getTeachersForSchool(School school){
        return teacherRepository.findBySchool(school);
    }
}
