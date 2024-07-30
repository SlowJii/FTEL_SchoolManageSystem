package com.FTEL.SchoolManagementSystem.repository;

import com.FTEL.SchoolManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long userId);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}

