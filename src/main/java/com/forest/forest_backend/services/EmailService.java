package com.forest.forest_backend.services;

import com.forest.forest_backend.config.GmailAuthHelper;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EmailService {

    private final Gmail gmail;

    public EmailService() throws Exception {
        this.gmail = GmailAuthHelper.authorize();
    }

    // -----------------------------
    // SEND OTP EMAIL
    // -----------------------------
    @Async
    public void sendOtpEmail(String toEmail, String otpCode) {
        try {
            String subject = "Your Forest Cosmetics Verification Code";
            String body = """
                    Hello,

                    Your OTP is: %s

                    This OTP is valid for 10 minutes.

                    Regards,
                    Forest Cosmetics
                    """.formatted(otpCode);

            sendEmail(toEmail, subject, body);
            System.out.println("✅ OTP email sent to " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error sending OTP email: " + e.getMessage());
        }
    }

    // -----------------------------
    // SEND ORDER CONFIRMATION
    // -----------------------------
    @Async
    public void sendOrderConfirmation(String toEmail, String orderId) {
        try {
            String subject = "Order Confirmed: #" + orderId;
            String body = """
                    Thank you for your order!

                    Your order %s has been placed successfully.

                    Regards,
                    Forest Cosmetics
                    """.formatted(orderId);

            sendEmail(toEmail, subject, body);
            System.out.println("✅ Order confirmation email sent to " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error sending order email: " + e.getMessage());
        }
    }

    // -----------------------------
    // CORE SEND METHOD (GMAIL API)
    // -----------------------------
    private void sendEmail(String to, String subject, String bodyText) throws Exception {

        String emailContent =
                "To: " + to + "\r\n" +
                        "Subject: " + subject + "\r\n" +
                        "Content-Type: text/plain; charset=UTF-8\r\n\r\n" +
                        bodyText;

        byte[] encodedEmail = Base64.getUrlEncoder()
                .encode(emailContent.getBytes(StandardCharsets.UTF_8));

        Message message = new Message();
        message.setRaw(new String(encodedEmail));

        gmail.users().messages().send("me", message).execute();
    }
}
