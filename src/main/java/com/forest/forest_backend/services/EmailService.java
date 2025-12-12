package com.forest.forest_backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${RESEND_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public void sendOtpEmail(String toEmail, String otpCode) {
        try {
            String url = "https://api.resend.com/emails";

            Map<String, Object> body = new HashMap<>();
            body.put("from", "Forest App <onboarding@resend.dev>");
            body.put("to", toEmail);
            body.put("subject", "Your Forest App Verification Code");
            body.put("html", "<p>Your OTP is: <strong>" + otpCode + "</strong></p>");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            System.out.println("Resend response: " + response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending OTP email: " + e.getMessage());
        }
    }

    @Async
    public void sendOrderConfirmation(String toEmail, String orderId) {
        try {
            String url = "https://api.resend.com/emails";

            Map<String, Object> body = new HashMap<>();
            body.put("from", "Forest App <onboarding@resend.dev>");
            body.put("to", toEmail);
            body.put("subject", "Order Confirmed: #" + orderId);
            body.put("html",
                    "<p>Your order <strong>" + orderId + "</strong> has been placed!</p>");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            System.out.println("Resend response: " + response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending order email: " + e.getMessage());
        }
    }
}
