package com.school.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class StudentData {
    private Long id;
    private String address;
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private String grade;
    private ClassesData classInformation;
    private List<ResultsData> resultsData;
}
