package com.school.backend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultsData {
    private String term;
    private String grade;
    private List<MarksData> marks = new ArrayList<>();
}
