package com.school.backend.util;

public enum Terms {
    TERM1("Term 1"),
    TERM2("Term 2"),
    TERM3("Term 3"),
    TERM4("Term 4");

    private final String stringValue;
    Terms(final String stringValue){
        this.stringValue = stringValue;
    }
    public String toString() { return stringValue; }
}
