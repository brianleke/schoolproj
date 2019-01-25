package com.school.backend.repository;

import com.school.backend.entities.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    @Query("SELECT superAdmin FROM SuperAdmin superAdmin WHERE superAdmin.username=(:username) AND superAdmin.encryptedPassword=(:password)")
    SuperAdmin findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
