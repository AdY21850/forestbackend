package com.forest.forest_backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.services.emails.EmailRequest;
import com.resend.services.emails.EmailResponse;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    // -----------------------------
    //  SEND OTP EMAIL
    // -----------------------------
    @Async
    public void sendOtpEmail(String toEmail, String otpCode) {
        try {
            Resend resend = new Resend(apiKey);

            EmailRequest emailRequest = EmailRequest.builder()
                    .from("Forest App <onboarding@resend.dev>")
                    .to(toEmail)
                    .subject("Your Forest App Verification Code")
                    .html(
                            "<p>Hello,</p>" +
                                    "<p>Your OTP is: <strong>" + otpCode + "</strong></p>" +
                                    "<p>Valid for 10 minutes.</p>"
                    )
                    .build();

            EmailResponse response = resend.emails().send(emailRequest);

            System.out.println("OTP email sent to " + toEmail + " | ID: " + response.getId());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending OTP email: " + e.getMessage());
        }
    }

    // -----------------------------
    //  SEND ORDER CONFIRMATION
    // -----------------------------
    @Async
    public void sendOrderConfirmation(String toEmail, String orderId) {
        try {
            Resend resend = new Resend(apiKey);

            EmailRequest emailRequest = EmailRequest.builder()
                    .from("Forest App <onboarding@resend.dev>")
                    .to(toEmail)
                    .subject("Order Confirmed: #" + orderId)
                    .html(
                            "<p>Great news!</p>" +
                                    "<p>Your order <strong>#" + orderId + "</strong> has been placed successfully.</p>" +
                                    "<p>We will notify you when it ships.</p>"
                    )
                    .build();

            EmailResponse response = resend.emails().send(emailRequest);

            System.out.println("Order confirmation sent to " + toEmail + " | ID: " + response.getId());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending order email: " + e.getMessage());
        }
    }
}
