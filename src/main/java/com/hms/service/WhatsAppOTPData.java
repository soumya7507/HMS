package com.hms.service;

public class WhatsAppOTPData {
    private final String whatsappOtp;
    private final long expiryTime;
    public WhatsAppOTPData(String whatsappOtp, long expiryTime) {
        this.whatsappOtp=whatsappOtp;
        this.expiryTime = expiryTime;
    }

    public String getWhatsAppOtp() {
        return whatsappOtp;
    }

    public long getExpiryTime() {
        return expiryTime;
    }
}
