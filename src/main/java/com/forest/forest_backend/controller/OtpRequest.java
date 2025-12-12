package com.forest.forest_backend.controller;
import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private String otpCode;
}