package com.school.backend.util;

import com.school.backend.entities.*;
import com.school.backend.model.*;

import java.util.ArrayList;
import java.util.List;

public class EntityToDtoMapper {

    public static SchoolData schoolToSchoolDataMapper(School school){
        SchoolData dto = new SchoolData();
        dto.setId(school.getId());
        dto.setAddress(school.getAddress());
        dto.setName(school.getName());
        dto.setPostCode(school.getPostCode());
        dto.setPrincipalName(school.getPrincipalName());

        dto.setTeachers(teacherListFromTeacherEntityList(school.getSchoolTeachers()));
        dto.setAdmins(schoolAdminFromSchoolAdministratorsEntity(school.getSchoolAdministrators()));
        dto.setStudents(studentDataFromStudentEntity(school.getSchoolStudents()));
        dto.setClasses(classesDataFromClassesEntity(school.getSchoolClasses()));
        return dto;
    }


    public static List<TeacherData> teacherListFromTeacherEntityList(List<Teacher> teachers){
        List<TeacherData> teacherList = new ArrayList<>();

        for(Teacher teacher: teachers){
            TeacherData teacherData = new TeacherData();
            teacherData.setFirstName(teacher.getFirstName());
            teacherData.setLastName(teacher.getLastName());
            teacherData.setId(teacher.getId());

            teacherList.add(teacherData);
        }

        return teacherList;
    }

    public static TeacherData teacherFromTeacherEntityList(Teacher teacher){
        TeacherData teacherData = new TeacherData();
        teacherData.setFirstName(teacher.getFirstName());
        teacherData.setLastName(teacher.getLastName());
        teacherData.setId(teacher.getId());

        return teacherData;
    }

    public static List<SchoolAdmin> schoolAdminFromSchoolAdministratorsEntity(List<SchoolAdministrators> schoolAdministrators){
        List<SchoolAdmin> schoolAdminList = new ArrayList<>();

        for(SchoolAdministrators schoolAdministrator: schoolAdministrators){
            SchoolAdmin schoolAdmin = new SchoolAdmin();
            schoolAdmin.setUsername(schoolAdministrator.getUsername());
            schoolAdmin.setLastName(schoolAdministrator.getLastName());
            schoolAdmin.setId(schoolAdministrator.getId());
            schoolAdmin.setFirstName(schoolAdministrator.getFirstName());

            schoolAdminList.add(schoolAdmin);
        }

        return schoolAdminList;
    }

    public static List<StudentData> studentDataFromStudentEntity(List<Student> students){
        List<StudentData> studentDataList = new ArrayList<>();

        for(Student student: students){
            StudentData studentData = new StudentData();
            studentData.setAddress(student.getAddress());
            studentData.setFirstName(student.getFirstName());
            studentData.setId(student.getId());
            studentData.setIdentificationNumber(student.getIdentificationNumber());
            studentData.setLastName(student.getLastName());
            studentData.setClassInformation(classesDataFromClassesEntity(student.getStudentClass()));

            studentDataList.add(studentData);
        }

        return studentDataList;
    }

    public static StudentData studentDataFromStudentEntity(Student student){
        StudentData studentData = new StudentData();
        studentData.setAddress(student.getAddress());
        studentData.setFirstName(student.getFirstName());
        studentData.setId(student.getId());
        studentData.setIdentificationNumber(student.getIdentificationNumber());
        studentData.setLastName(student.getLastName());
        studentData.setClassInformation(classesDataFromClassesEntity(student.getStudentClass()));
        studentData.setResultsData(resultsDataFromResultsEntity(student.getStudentResults()));
        return studentData;
    }

    public static ClassesData classesDataFromClassesEntity(Classes classObject){
        ClassesData classesData = new ClassesData();
        classesData.setClassName(classObject.getClassName());
        classesData.setGrade(classObject.getGrade());
        classesData.setId(classObject.getId());

        return classesData;
    }

    private static List<ResultsData> resultsDataFromResultsEntity(List<Results> resultsList){
        List<ResultsData> resultsDataList = new ArrayList<>();

        for(Results results: resultsList) {
            ResultsData resultsData = new ResultsData();
            resultsData.setGrade(results.getGrade());
            resultsData.setTerm(results.getTerm());
            resultsData.setMarks(marksDataListFromMarksEntity(results.getMarks()));

            resultsDataList.add(resultsData);
        }

        return resultsDataList;
    }

    private static List<MarksData> marksDataListFromMarksEntity(List<Marks> marks){
        List<MarksData> marksDataList = new ArrayList<>();

        for(Marks mark: marks){
            if(mark.getSubject().getTeacher() != null) {
                MarksData marksData = new MarksData();
                marksData.setScore(mark.getScore());
                marksData.setSubject(subjectDataFromSubject(mark.getSubject()));

                marksDataList.add(marksData);
            }
        }

        return marksDataList;
    }

    public static List<SubjectData> subjectDataListFromSubjectLists(List<Subject> subjectList){
        List<SubjectData> subjectDataList = new ArrayList<>();

        for(Subject subject: subjectList){
            subjectDataList.add(subjectDataFromSubject(subject));
        }

        return subjectDataList;
    }

    private static SubjectData subjectDataFromSubject(Subject subject){
        SubjectData subjectData = new SubjectData();
        subjectData.setId(subject.getId());
        subjectData.setGrade(subject.getGrade());
        subjectData.setName(subject.getName());
        subjectData.setTeacher(teacherFromTeacherEntityList(subject.getTeacher()));
        return subjectData;
    }

    public static List<ClassesData> classesDataFromClassesEntity(List<Classes> classes){
        List<ClassesData> classesDataList = new ArrayList<>();

        for(Classes classObject: classes){
            ClassesData classesData = new ClassesData();
            classesData.setClassName(classObject.getClassName());
            classesData.setGrade(classObject.getGrade());
            classesData.setId(classObject.getId());

            classesDataList.add(classesData);
        }

        return classesDataList;
    }
}
