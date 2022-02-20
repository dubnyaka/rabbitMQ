package com.example.emailservice.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@Document(collection = "emails")
public class EmailData {

    @Id
    private String id;

    private String content;
    /**
     * Статус отправки письма
     * PENDING - ожидает отправки
     * SENT - успешная отправка
     * ERROR - ошибка при отправке
     */
    private String status;

    /**
     * В случае ошибки, записывать текст сообщения об ошибке
     */
    private String errorDescription;

}
