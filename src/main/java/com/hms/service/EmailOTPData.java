package com.hms.service;

public class EmailOTPData {
    private final String emailOtp;
    private final long expiryTime;

    public EmailOTPData(String emailOtp, long expiryTime) {
        this.emailOtp = emailOtp;
        this.expiryTime = expiryTime;
    }

    public String getEmailOtp() {
        return emailOtp;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }
}
