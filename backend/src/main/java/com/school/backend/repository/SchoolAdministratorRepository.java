package com.school.backend.repository;

import com.school.backend.entities.School;
import com.school.backend.entities.SchoolAdministrators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolAdministratorRepository extends JpaRepository<SchoolAdministrators, Long> {
    @Query("SELECT schoolAdmin FROM SchoolAdministrators schoolAdmin WHERE schoolAdmin.school=(:school) ORDER BY lastName ASC")
    List<SchoolAdministrators> findBySchool(@Param("school") School school);

    @Query("SELECT schoolAdmin FROM SchoolAdministrators schoolAdmin WHERE schoolAdmin.username=(:username) AND schoolAdmin.password=(:password)")
    SchoolAdministrators findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
