package com.school.backend.util;

public enum Grades {
    GRADER("Grade R"),
    GRADE0("Grade 0"),
    GRADE1("Grade 1"),
    GRADE2("Grade 2"),
    GRADE3("Grade 3"),
    GRADE4("Grade 4"),
    GRADE5("Grade 5"),
    GRADE6("Grade 6"),
    GRADE7("Grade 7"),
    GRADE8("Grade 8"),
    GRADE9("Grade 9"),
    GRADE10("Grade 10"),
    GRADE11("Grade 11"),
    GRADE12("Grade 12");

    private final String stringValue;
    Grades(final String stringValue){
        this.stringValue = stringValue;
    }
    public String toString() { return stringValue; }
}
