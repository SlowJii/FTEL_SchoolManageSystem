package com.FTEL.SchoolManagementSystem.controller;

import com.FTEL.SchoolManagementSystem.dto.request.UserCreationRequest;
import com.FTEL.SchoolManagementSystem.dto.request.UserUpdateRequest;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.service.UserService;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserCreationRequest request){
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId, Authentication authentication){
        String username = authentication.getName(); // Lấy tên người dùng đã xác thực
        User user = userService.updateUser(request, userId, username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId, Authentication authentication){
        String username = authentication.getName(); // Lấy tên người dùng đã xác thực
        return userService.getUserById(userId, username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
