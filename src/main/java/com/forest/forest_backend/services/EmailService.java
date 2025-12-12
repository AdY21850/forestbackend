package com.forest.forest_backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.services.emails.models.CreateEmailOptions;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    @Async
    public void sendOtpEmail(String toEmail, String otpCode) {
        try {
            Resend resend = new Resend(apiKey);

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("Forest App <onboarding@resend.dev>")  // FREE FROM DOMAIN
                    .to(toEmail)
                    .subject("Your Forest App Verification Code")
                    .html("<p>Hello,</p>" +
                            "<p>Your OTP is: <strong>" + otpCode + "</strong></p>" +
                            "<p>Valid for 10 minutes.</p>")
                    .build();

            resend.emails().send(params);

            System.out.println("OTP email sent to " + toEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
