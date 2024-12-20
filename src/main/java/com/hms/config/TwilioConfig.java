package com.hms.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

        @Value("${ASid.key}")
        private String token;

        @Value("${token.password}")
        private String password;

        @Bean
        public void init(){
            Twilio.init(token, password);
        }
}
