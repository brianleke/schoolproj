package com.school.backend.model;

import lombok.Data;

@Data
public class SubjectData {
    private Long id;
    private String name;
    private String grade;
    private TeacherData teacher;
}
