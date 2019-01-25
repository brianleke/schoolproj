package com.school.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class SchoolData {
    private Long id;
    private String name;
    private String address;
    private String postCode;
    private String province;
    private String principalName;
    private List<TeacherData> teachers;
    private List<SchoolAdmin> admins;
    private List<StudentData> students;
    private List<ClassesData> classes;
}
