package com.forest.forest_backend.controller;

import com.forest.forest_backend.entity.User;
import com.forest.forest_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        // Prevent duplicate emails
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email already taken");
        }
        return userRepository.save(user);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User Not Found"));
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody OtpRequest request) {
        // Mock OTP check
        if ("1234".equals(request.getOtpCode())) {
            return "SUCCESS";
        } else {
            throw new RuntimeException("Invalid OTP Code");
        }
    }
}