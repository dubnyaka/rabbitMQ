package com.example.messagingrabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Publisher {
    private final static String QUEUE_NAME = "emailsToSend";

    public static void addEmailIdToSendQueue(String id) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, id.getBytes());
            System.out.println(" [x] Отправлено в очередь на отправку '" + id + "'");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
