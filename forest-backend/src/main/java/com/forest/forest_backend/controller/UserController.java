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

    @PostMapping
    public User registerUser(@RequestBody User user){
        return userRepository.save(user);
    }
    @GetMapping("/phone/{phoneNumber}")
    public User getUserByPhone(@PathVariable String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new RuntimeException("user Not Found"));
    }
}
