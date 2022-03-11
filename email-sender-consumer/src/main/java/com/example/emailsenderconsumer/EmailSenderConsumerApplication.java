package com.example.emailsenderconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EmailSenderConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailSenderConsumerApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail() {
        Consumer consumer = new Consumer();
        consumer.consume();
    }
}























