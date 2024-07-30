package com.FTEL.SchoolManagementSystem.service;

import com.FTEL.SchoolManagementSystem.dto.request.CourseRequest;
import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public Course createCourse(CourseRequest request){
        Course course = new Course();

        course.setCourseName(request.getCourseName());
        course.setCredit(request.getCredit());

        return courseRepository.save(course);
    }

    public Course updateCourse(CourseRequest request, Long courseId){
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        if (request.getCourseName() != null) {
            course.setCourseName(request.getCourseName());
        }

        if (request.getCredit() != null) {
            course.setCredit(request.getCredit());
        }

        return courseRepository.save(course);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long courseId){
        return  courseRepository.findById(courseId);
    }

    public void deleteCourse(Long courseId){
        if(!courseRepository.existsById(courseId)){
            throw new RuntimeException("Course not found");
        }

        courseRepository.deleteById(courseId);
    }

    public Set<User> getUsersByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        return course.getUsers();
    }

    public Course getMostPopularCourse() {
        return courseRepository.findAll().stream()
                .max(Comparator.comparingInt(course -> course.getUsers().size()))
                .orElseThrow(() -> new RuntimeException("No courses found"));
    }
}