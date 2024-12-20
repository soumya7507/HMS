package com.hms.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String to,String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true = multipart

        // Set basic email details

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message);

        // Attach the PDF file
//        File pdfFile = new File("C:\\Users\\yogas\\Downloads\\feeReciept.pdf");
//        helper.addAttachment("YourFile.pdf", pdfFile);

        // Send the email
        javaMailSender.send(mimeMessage);
    }
    public void senBookingEmail(String pdf,String to, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true = multipart

        // Set basic email details

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message);

        // Attach the PDF file
        File pdfFile = new File(pdf);
        helper.addAttachment("YourFile.pdf", pdfFile);

        // Send the email
        javaMailSender.send(mimeMessage);
    }
}
