package com.forest.forest_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // @Async is CRITICAL for industry-ready apps.
    // Sending email is slow. This tells Spring to run it in the background
    // so the user doesn't have to wait for the UI to unfreeze.
    @Async
    public void sendOtpEmail(String toEmail, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Forest App Verification Code");
        message.setText("Hello,\n\n" +
                "Your verification code for Forest is: " + otpCode + "\n\n" +
                "This code is valid for 10 minutes.\n" +
                "Welcome to the community!");

        mailSender.send(message);
        System.out.println("OTP Email sent to " + toEmail);
    }

    @Async
    public void sendOrderConfirmation(String toEmail, String orderId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Order Confirmed: #" + orderId);
        message.setText("Great news! Your order has been placed successfully.\n" +
                "We will notify you when it ships.");

        mailSender.send(message);
    }
}