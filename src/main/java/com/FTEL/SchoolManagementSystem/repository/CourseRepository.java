package com.FTEL.SchoolManagementSystem.repository;

import com.FTEL.SchoolManagementSystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsById(Long courseId);
}
