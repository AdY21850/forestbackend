package com.forest.forest_backend.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${MAIL_FROM}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // -----------------------------
    // SEND OTP EMAIL
    // -----------------------------
    @Async
    public void sendOtpEmail(String toEmail, String otpCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject("Your Forest Cosmetics Verification Code");
            helper.setText(
                    "<p>Hello,</p>" +
                            "<p>Your OTP is: <strong>" + otpCode + "</strong></p>" +
                            "<p>This OTP is valid for 10 minutes.</p>",
                    true
            );

            mailSender.send(message);
            System.out.println("OTP email sent to " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending OTP email: " + e.getMessage());
        }
    }

    // -----------------------------
    // SEND ORDER CONFIRMATION
    // -----------------------------
    @Async
    public void sendOrderConfirmation(String toEmail, String orderId) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject("Order Confirmed: #" + orderId);
            helper.setText(
                    "<p>Thank you for your order!</p>" +
                            "<p>Your order <strong>" + orderId + "</strong> has been placed successfully.</p>",
                    true
            );

            mailSender.send(message);
            System.out.println("Order confirmation email sent to " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending order email: " + e.getMessage());
        }
    }
}
