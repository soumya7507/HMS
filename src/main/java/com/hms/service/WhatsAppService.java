package com.hms.service;

import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    @Value("${whatsapp.number}")
    private String fromNumber;
    public String sendWhatsAppMessage(String to, String messageContent) {
        try {
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:"+to),
                            new com.twilio.type.PhoneNumber("whatsapp:"+fromNumber),
                            messageContent
                    )
                    .create();

            return "Message sent successfully! SID: " + message.getSid();
        } catch (Exception e) {
            return "Error sending message: " + e.getMessage();
        }
    }
}
