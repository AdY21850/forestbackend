package com.forest.forest_backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.models.Email;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    @Async
    public void sendOtpEmail(String toEmail, String otpCode) {

        try {
            Resend resend = new Resend(apiKey);

            Email email = Email.builder()
                    .from("Forest App <onboarding@resend.dev>")
                    .to(toEmail)
                    .subject("Your Forest App Verification Code")
                    .html("<p>Your OTP is: <strong>" + otpCode + "</strong></p>")
                    .build();

            resend.emails().send(email);

            System.out.println("OTP sent!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Email sending failed: " + e.getMessage());
        }
    }
}
