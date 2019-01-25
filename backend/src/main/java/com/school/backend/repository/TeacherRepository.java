package com.school.backend.repository;

import com.school.backend.entities.School;
import com.school.backend.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT teacher FROM Teacher teacher WHERE teacher.school=(:school) ORDER BY lastName ASC")
    List<Teacher> findBySchool(@Param("school") School school);
}
