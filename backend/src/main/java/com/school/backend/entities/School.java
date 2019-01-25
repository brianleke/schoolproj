package com.school.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "School")
@Table(name = "school")
public class School {
    private @Id
    @GeneratedValue
    Long id;

    private String name;
    private String address;
    private String postCode;
    private String province;
    private String principalName;

    @OneToMany(mappedBy = "school")
    @JsonBackReference
    private List<SchoolAdministrators> schoolAdministrators = new ArrayList<>();

    @OneToMany(mappedBy = "school")
    @JsonBackReference(value = "schoolTeacher")
    private List<Teacher> schoolTeachers = new ArrayList<>();

    @OneToMany(mappedBy = "school")
    @JsonBackReference(value = "schoolStudents")
    private List<Student> schoolStudents = new ArrayList<>();

    @OneToMany(mappedBy = "school")
    @JsonBackReference(value = "schoolClasses")
    private List<Classes> schoolClasses = new ArrayList<>();

}
