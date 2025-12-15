package com.forest.forest_backend.services;

import com.forest.forest_backend.config.GmailAuthHelper;
import com.google.api.services.gmail.Gmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // ✅ SAFE empty constructor
    public EmailService() {
    }

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            // ✅ Gmail client created ONLY when needed
            Gmail gmail = GmailAuthHelper.authorize();

            String subject = "OTP Verification";
            String body =
                    "Your OTP is: " + otp + "\n\n" +
                            "This OTP is valid for 10 minutes.\n\n" +
                            "If you did not request this, please ignore this email.";

            GmailAuthHelper.sendEmail(
                    gmail,
                    "me",          // authenticated Gmail user
                    toEmail,
                    subject,
                    body
            );

            System.out.println("✅ OTP email sent to: " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to send OTP email");
        }
    }
}
