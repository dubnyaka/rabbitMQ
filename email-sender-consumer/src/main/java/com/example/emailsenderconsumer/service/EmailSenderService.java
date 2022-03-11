package com.example.emailsenderconsumer.service;

import com.example.emailsenderconsumer.data.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailData emailData){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("dubnyaka@gmail.com");
        message.setTo(emailData.getReceiver());
        message.setText(emailData.getBody());
        message.setSubject(emailData.getSubject());

        mailSender.send(message);
        System.out.println(" Mail send to " + emailData.getReceiver());
    }
}


























