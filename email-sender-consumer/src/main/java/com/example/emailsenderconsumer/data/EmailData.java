package com.example.emailsenderconsumer.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@Setter
public class EmailData {

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
    @JsonIgnore
    private String status;

    /**
     * В случае ошибки, записывать текст сообщения об ошибке
     */
    @JsonIgnore
    private String errorDescription;

}
