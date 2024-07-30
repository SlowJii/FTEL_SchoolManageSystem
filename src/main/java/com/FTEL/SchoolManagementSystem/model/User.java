package com.FTEL.SchoolManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String userEmail;

    Set<String> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_registered_course",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonBackReference
    private Set<Course> courses = new HashSet<>();

}
