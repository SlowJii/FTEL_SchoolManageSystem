package com.FTEL.SchoolManagementSystem.service;

import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.repository.CourseRepository;
import com.FTEL.SchoolManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseUserService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Course addUserToCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        if (course.getUsers().contains(user)) {
            throw new RuntimeException("User already registered for this course!");
        }
        if (course.getSeatAvailable() <= 0) {
            throw new RuntimeException("No seats available for this course!");
        }

        course.getUsers().add(user);
        course.setSeatAvailable(course.getSeatAvailable() - 1);
        user.getCourses().add(course);

        courseRepository.save(course);
        userRepository.save(user);

        return course;
    }

    @Transactional
    public Course removeUserFromCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getUsers().contains(user)) {
            throw new RuntimeException("User is not registered for this course!");
        }

        course.getUsers().remove(user);
        course.setSeatAvailable(course.getSeatAvailable() + 1);
        user.getCourses().remove(course);

        courseRepository.save(course);
        userRepository.save(user);

        return course;
    }
}
