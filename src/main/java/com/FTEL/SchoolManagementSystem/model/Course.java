package com.FTEL.SchoolManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String courseName;
    private int credit;
    private int seatAvailable = 10;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<User> users = new HashSet<>();
}