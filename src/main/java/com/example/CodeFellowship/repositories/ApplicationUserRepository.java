package com.example.CodeFellowship.repositories;

import com.example.CodeFellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
    List<ApplicationUser> findByUsernameNot(String username);
}
