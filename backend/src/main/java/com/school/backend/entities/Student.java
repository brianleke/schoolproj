package com.school.backend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="students")
public class Student {
    private @Id
    @GeneratedValue
    Long id;
    private String address;
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private String studyYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", referencedColumnName = "id",nullable = false)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", referencedColumnName = "id",nullable = false)
    private Classes studentClass;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "results_id", referencedColumnName = "id")
    private List<Results> studentResults = new ArrayList<>();
}
