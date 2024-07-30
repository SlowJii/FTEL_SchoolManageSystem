package com.FTEL.SchoolManagementSystem.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserUpdateRequest {
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
