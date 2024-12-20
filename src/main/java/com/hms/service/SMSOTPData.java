package com.hms.service;

public class SMSOTPData {
    private final String smsOtp;
    private final long expiryTime;

    public long getExpiryTime() {
        return expiryTime;
    }

    public String getSmsOtp() {
        return smsOtp;
    }

    public SMSOTPData(String smsOtp, long expiryTime) {
        this.smsOtp = smsOtp;
        this.expiryTime = expiryTime;
    }
}
