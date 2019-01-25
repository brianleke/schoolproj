package com.school.backend.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="subjects")
public class Subject {
    private @Id
    @GeneratedValue
    Long id;

    private String name;
    private String grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
}
