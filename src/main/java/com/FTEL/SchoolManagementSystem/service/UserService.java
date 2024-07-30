package com.FTEL.SchoolManagementSystem.service;

import com.FTEL.SchoolManagementSystem.Utils.StringUtils;
import com.FTEL.SchoolManagementSystem.dto.request.UserCreationRequest;
import com.FTEL.SchoolManagementSystem.dto.request.UserUpdateRequest;
import com.FTEL.SchoolManagementSystem.enums.Role;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest userCreationRequest){
        User user = new User();

        user.setUsername(userCreationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        user.setFirstName(userCreationRequest.getFirstName());
        user.setLastName(userCreationRequest.getLastName());
        user.setDob(userCreationRequest.getDob());

        String email = StringUtils.generateUserEmail(userCreationRequest.getFirstName(), userCreationRequest.getLastName());
        user.setUserEmail(email);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.STAFF.name());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @PreAuthorize("#username == authentication.name")
    public User updateUser(UserUpdateRequest request, Long userId, String username){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!username.equals(user.getUsername())) {
            throw new RuntimeException("Bạn không có quyền cập nhật người dùng này");
        }

        boolean firstNameChanged = false;
        boolean lastNameChanged = false;

        if (request.getPassword() != null){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getUsername() != null){
            user.setUsername(request.getUsername());
        }

        if (request.getFirstName() != null){
            user.setFirstName(request.getFirstName());
            firstNameChanged = true;
        }

        if (request.getLastName() != null){
            user.setLastName(request.getLastName());
            lastNameChanged = true;
        }

        if (request.getDob() != null){
            user.setDob(request.getDob());
        }

        if (firstNameChanged || lastNameChanged) {
            String email = StringUtils.generateUserEmail(user.getFirstName(), user.getLastName());
            user.setUserEmail(email);
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    @PreAuthorize("#username == authentication.name")
    public Optional<User> getUserById(Long userId, String username){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && !username.equals(user.get().getUsername())) {
            throw new RuntimeException("Bạn không có quyền xem người dùng này");
        }
        return user;
    }

    public void deleteUser(Long userId){
        if (!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    private void checkUserAuthorization(String username) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            if (!username.equals(((UserDetails) principal).getUsername())) {
                throw new RuntimeException("Bạn không có quyền truy cập thông tin người dùng này");
            }
        } else {
            throw new RuntimeException("Xác thực không hợp lệ");
        }
    }

}
