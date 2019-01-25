package com.school.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="results")
public class Results {
    private @Id
    @GeneratedValue
    Long id;

    private String term;
    private String grade;

    @OneToMany(mappedBy = "results")
    @JsonBackReference
    private List<Marks> marks = new ArrayList<>();
}
