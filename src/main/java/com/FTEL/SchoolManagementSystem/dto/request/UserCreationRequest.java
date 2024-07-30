package com.FTEL.SchoolManagementSystem.dto.request;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserCreationRequest {
    @Size(min = 3, message = "INVALID USERNAME, USERNAME AVAILABLE AT LEAST 3 CHARACTERS")
    private String username;
    @Size(min = 8, message = "INVALID PASSSWORD, PASSWORD NEED AT LEAST 8 CHARACTERS !")
    private String password;

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String userEmail;

    private Set<String> roles;
}
