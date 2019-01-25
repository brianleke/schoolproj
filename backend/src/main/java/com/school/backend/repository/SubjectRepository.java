package com.school.backend.repository;

import com.school.backend.entities.School;
import com.school.backend.entities.SchoolAdministrators;
import com.school.backend.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT subject FROM Subject subject WHERE subject.school=(:school) AND subject.grade=(:grade)")
    List<Subject> findBySchoolAndGrade(@Param("school") School school, @Param("grade") String grade);
}
