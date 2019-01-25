package com.school.backend.service;

import com.school.backend.entities.School;
import com.school.backend.entities.Subject;
import com.school.backend.entities.Teacher;
import com.school.backend.exceptions.GenericNotFoundException;
import com.school.backend.exceptions.InvalidLoginTokenException;
import com.school.backend.exceptions.SchoolNotFoundException;
import com.school.backend.model.SubjectData;
import com.school.backend.repository.SchoolRepository;
import com.school.backend.repository.SubjectRepository;
import com.school.backend.repository.TeacherRepository;
import com.school.backend.util.EntityToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private LoginService loginService;

    public Subject createNewSubject(String token, Long teacherId, Long schoolId, Subject newSubject){
        if(loginService.validateToken(token)) {
            Optional<School> schoolFromId = schoolRepository.findById(schoolId);
            Optional<Teacher> teacherFromId = teacherRepository.findById(teacherId);
            schoolFromId.ifPresent(newSubject::setSchool);
            teacherFromId.ifPresent(newSubject::setTeacher);
            return subjectRepository.save(newSubject);
        }
        else {
            throw new InvalidLoginTokenException();
        }
    }

    public Subject updateSubject(String token, Long teacherId, Long subjectId){
        tokenIsValid(token);
        Optional<Teacher> teacherFromId = teacherRepository.findById(teacherId);
        Optional<Subject> subjectById = subjectRepository.findById(subjectId);

        if(subjectById.isPresent()){
            Subject subject = subjectById.get();
            teacherFromId.ifPresent(subject::setTeacher);

            return subjectRepository.save(subject);
        }
        else {
            throw new GenericNotFoundException("Subject does not exists!");
        }
    }

    public Map<String, List<SubjectData>> retrieveSubjectsForGrade(Long schoolId, String token){
        tokenIsValid(token);
        Optional<School> schoolFromId = schoolRepository.findById(schoolId);

        String[] grades = {"R", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        Map<String, List<SubjectData>> allSubjectsForGrades = new HashMap<>();

        if(schoolFromId.isPresent()){
            School school = schoolFromId.get();
            for(String grade: grades) {
                List<Subject> allSubjectsForGrade = subjectRepository.findBySchoolAndGrade(school, grade);
                allSubjectsForGrades.put(grade, EntityToDtoMapper.subjectDataListFromSubjectLists(allSubjectsForGrade));
            }
        }

        return allSubjectsForGrades;
    }

    private void tokenIsValid(String token){
        if(!loginService.validateToken(token)){
            throw new InvalidLoginTokenException();
        }
    }
}
