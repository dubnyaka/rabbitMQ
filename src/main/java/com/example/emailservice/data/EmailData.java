package com.example.emailservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Jacksonized
@Builder
@Getter
@Setter
@Document(collection = "emails")
public class EmailData {

    @JsonProperty("id")
    @Id
    private String id;

    private String subject;

    private String body;

    private String receiver;

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
