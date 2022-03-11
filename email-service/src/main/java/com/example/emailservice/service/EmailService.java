package com.example.emailservice.service;

import com.example.emailservice.dao.EmailDao;
import com.example.emailservice.data.EmailData;
import com.example.messagingrabbitmq.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Service
@RequiredArgsConstructor
public class EmailService {

    private final static String STATUS_PENDING = "PENDING";

    @Autowired
    private final EmailDao personDao;

    public EmailData read(String id) {
        return personDao.findById(id).orElse(null);
    }

    public String create(EmailData emailData) {
        String createdEmailId = personDao.insert(
                EmailData.builder()
                        .status(STATUS_PENDING)
                        .subject(emailData.getSubject())
                        .body(emailData.getBody())
                        .receiver(emailData.getReceiver())
                        .errorDescription("")
                        .build()
        ).getId();

        if (!createdEmailId.isEmpty()) {
            Publisher.addEmailIdToSendQueue(createdEmailId);
        }
        return createdEmailId;
    }
}
