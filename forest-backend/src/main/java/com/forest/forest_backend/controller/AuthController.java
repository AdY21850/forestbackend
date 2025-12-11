package com.forest.forest_backend.controller;

// FIX: Importing from the correct package
import com.forest.forest_backend.services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        // 1. Generate random 6-digit OTP
        String otp = String.format("%04d", new Random().nextInt(9999));

        // 2. Send Email
        try {
            emailService.sendOtpEmail(email, otp);
            return "OTP sent successfully to " + email;
        } catch (Exception e) {
            e.printStackTrace(); // In production, use Logger, but fine for dev
            return "Error sending OTP: " + e.getMessage();
        }
    }
}