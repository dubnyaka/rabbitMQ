package com.example.emailsenderconsumer;

import com.example.emailsenderconsumer.data.EmailData;
import com.example.emailsenderconsumer.service.EmailSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Component
public class Consumer {
    private final static String QUEUE_NAME = "emailsToSend";
    private final static String STATUS_ERROR = "ERROR";
    private final static String STATUS_SENT = "SENT";

    @Autowired
    private static EmailSenderService senderService;

    @Autowired
    public Consumer(EmailSenderService senderService) {
        this.senderService = senderService;
    }

    public void consume() {

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String emailId = new String(delivery.getBody(), StandardCharsets.UTF_8);
            EmailData emailToSent = getEmailById(emailId);
            if (emailToSent != null) {
                senderService.sendEmail(emailToSent);
                setEmailStatus(emailId, STATUS_SENT, "");
                System.out.println(" [x] Sent '" + emailId + "'");
            } else {
                setEmailStatus(emailId, STATUS_ERROR, "Error getting mail from db");
                System.out.println(" [x] Error '" + emailId + "'");
            }
        };

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }


    private static void setEmailStatus(String id, String status, String errorDescription) throws IOException {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("status", status);
        if (status.equals(STATUS_ERROR)) {
            json.put("errorDescription", errorDescription);
        }

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPut request = new HttpPut("http://localhost:8090/emails/");
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        }
    }

    private static EmailData getEmailById(String id) throws IOException {
        String url = "http://localhost:8090/emails/" + id;
        ObjectMapper mapper = new ObjectMapper();
        try {
            EmailData emailData = mapper.readValue(new URL(url), EmailData.class);
            return emailData;
        } catch (Exception e) {
            setEmailStatus(id, STATUS_ERROR, e.toString());
            e.printStackTrace();
        }
        return null;
    }

}
