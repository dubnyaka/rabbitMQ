package com.example.emailservice.service;

import com.example.emailservice.dao.EmailDao;
import com.example.emailservice.data.EmailData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private EmailDao personDao;

    public EmailData read(String id) {
        return personDao.findById(id).orElse(null);
    }

    public String create(String content) {
        return personDao.insert(
                EmailData.builder()
                        .status("PENDING")
                        .content(content)
                        .errorDescription("")
                        .build()
        ).getId();
    }
}
