package com.forest.forest_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_storage")
public class OtpEntity {

    @Id
    private String email;
    private String otpCode;
    private LocalDateTime createdAt;


    public OtpEntity() {}


    public OtpEntity(String email, String otpCode) {
        this.email = email;
        this.otpCode = otpCode;
        this.createdAt = LocalDateTime.now();
    }


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}