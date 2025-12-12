package com.forest.forest_backend.controller;

import com.forest.forest_backend.entity.OnboardingItem;
import com.forest.forest_backend.repository.OnboardingRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/onboarding")
public class OnboardingController {
    private final OnboardingRepository repository;

    public OnboardingController(OnboardingRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public OnboardingItem addSlide(@RequestBody OnboardingItem item) {
        return repository.save(item);
    }

    @GetMapping
    public List<OnboardingItem> getSlides() {
        return repository.findAll();
    }
    @GetMapping("/ping")
    public String ping() {
        return "Pong"; // Returns a tiny string, uses almost 0 memory/cpu
    }
}