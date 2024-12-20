package com.hms.service;

import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
        @Value("${phone.number}")
        private String fromNumber;

        public String sendSms(String to,String content){
            Message message=Message.creator(
                    new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(fromNumber),
                    content
            ).create();
            return message.getSid();
        }
}
