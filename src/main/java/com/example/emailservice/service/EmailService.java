package com.example.emailservice.service;

import com.example.emailservice.dao.EmailDao;
import com.example.emailservice.data.EmailData;
import com.example.messagingrabbitmq.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final static String STATUS_PENDING = "PENDING";

    @Autowired
    private final EmailDao personDao;

    public EmailData read(String id) {
        return personDao.findById(id).orElse(null);
    }

    public String create(String content) {
        String createdEmailId = personDao.insert(
                EmailData.builder()
                        .status(STATUS_PENDING)
                        .content(content)
                        .errorDescription("")
                        .build()
        ).getId();

        if (!createdEmailId.isEmpty()) {
            Publisher.addEmailIdToSendQueue(createdEmailId);
        }
        return createdEmailId;
    }

    public void setStatus(EmailData emailData) {
        Optional<EmailData> optionalEmailData = personDao.findById(emailData.getId());
        if (optionalEmailData.isPresent()) {
            EmailData emailDataFromDb = optionalEmailData.get();
            if (emailData.getStatus().equals("ERROR")) {
                emailDataFromDb.setStatus(emailData.getStatus());
                emailDataFromDb.setErrorDescription(emailData.getErrorDescription());
            } else {
                emailDataFromDb.setStatus("SENT");
            }
            personDao.save(emailDataFromDb);
        }
    }
}
