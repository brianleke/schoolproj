package com.school.backend.exceptions;

public class SchoolNotFoundException extends RuntimeException{
    public SchoolNotFoundException(Long id){
        super("Could not find school with id: " + id);
    }
}
