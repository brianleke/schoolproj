package com.school.backend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="classes")
public class Classes {
    private @Id
    @GeneratedValue
    Long id;

    private String className;
    private String grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", referencedColumnName = "id", nullable = false)
    private School school;
}
