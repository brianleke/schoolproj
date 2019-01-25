package com.school.backend.repository;

import com.school.backend.entities.Results;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultsRepository extends JpaRepository<Results, Long> {
}
