package com.hms.service;

import com.hms.util.OTPUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService {

    @Autowired
    private OTPUtil otpUtil;
    @Autowired
    private TwilioService twilioService;
    @Autowired
    private WhatsAppService whatsAppService;
    @Autowired
    private EmailService emailService;


    private final Map<String, SMSOTPData> otpStorage = new HashMap<>();
    private final Map<String, WhatsAppOTPData> otpStorage2 = new HashMap<>();
    private final Map<String, EmailOTPData> otpStorage3 = new HashMap<>();
    private static final long OTP_EXPIRY_TIME = 5 * 60 * 1000; // OTP expiry time: 5 minutes



    //For SMS OTP
    public String generateOTP(String phoneNumber) {
        String otp = otpUtil.generateOTP();
        otpStorage.put(phoneNumber, new SMSOTPData(otp, System.currentTimeMillis() + OTP_EXPIRY_TIME));
        twilioService.sendSms(phoneNumber, "Your OTP number is :" + otp);
//        OTPDb otpDb=new OTPDb();
//        otpDb.setPhoneNumber(phoneNumber);
//        otpDb.setOtp(otp);
//        otpDb.setExpiryTime(OTP_EXPIRY_TIME);
//        otpDbRepository.save(otpDb);

        return otp;
    }



    //For Whatsapp OTP
    public String generateWhatsappOTP(String phoneNumber) {
        String otp = otpUtil.generateOTP();
        otpStorage2.put(phoneNumber, new WhatsAppOTPData(otp, System.currentTimeMillis() + OTP_EXPIRY_TIME));
        whatsAppService.sendWhatsAppMessage(phoneNumber, "Your OTP number is :" + otp);
        return otp;
    }

    public String generateEmailOtp(String email) {
        try {
            String emailOtp = otpUtil.generateOTP();
            otpStorage3.put(email, new EmailOTPData(emailOtp, System.currentTimeMillis() + OTP_EXPIRY_TIME));
            emailService.sendEmail(email, "For Login Purpose", "Your OTP is :" + emailOtp);
            return emailOtp;
        } catch (Exception e) {
            e.getMessage();
        }
       return email;
    }
        //Validating OTP
    public boolean validateOTP(String mobileNumber,String otp) {
        SMSOTPData storedOTPData = otpStorage.get(mobileNumber);
        WhatsAppOTPData storedOTPData2 = otpStorage2.get(mobileNumber);

        if (storedOTPData == null && storedOTPData2 == null) {
            return false;
        }
        if (System.currentTimeMillis() > storedOTPData.getExpiryTime()) {
            otpStorage.remove(mobileNumber);
            return false;
        }
        if (storedOTPData.getSmsOtp().equals(otp) || storedOTPData2.getWhatsAppOtp().equals(otp)) {
            otpStorage.remove(mobileNumber);
            return true;
        }
//        else{
//            Optional<OTPDb> byMobile = otpDbRepository.findByPhoneNumber(mobileNumber);
//            otpDbRepository.deleteById(byMobile.get().getId());
//        }
        return false;
    }

    public boolean validateEmailOtp(String email, String otp){
        EmailOTPData emailOTPData = otpStorage3.get(email);
        if(emailOTPData==null){
            return false;
        }
        if(System.currentTimeMillis()>emailOTPData.getExpiryTime()){
            otpStorage3.remove(email);
            return false;
        }
        if(emailOTPData.getEmailOtp().equals(otp)){
            otpStorage3.remove(email);
            return true;
        }
        return false;
    }
}

