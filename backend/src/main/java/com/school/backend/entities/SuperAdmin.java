package com.school.backend.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "SuperAdmin")
@Table(name = "superadmins")
public class SuperAdmin {
    private @Id
    @GeneratedValue
    Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String encryptedPassword;
}
