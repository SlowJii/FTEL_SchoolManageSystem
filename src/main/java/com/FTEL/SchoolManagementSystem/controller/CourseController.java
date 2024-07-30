package com.FTEL.SchoolManagementSystem.controller;

import com.FTEL.SchoolManagementSystem.dto.request.CourseRequest;
import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequest request){
        Course course = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @PutMapping("{courseId}")
    public ResponseEntity<Course> updateCourse(@RequestBody CourseRequest request, @PathVariable Long courseId){
        Course course = courseService.updateCourse(request, courseId);
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId){
        return courseService.getCourseById(courseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
