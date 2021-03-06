package com.example.emailservice.controller;

import com.example.emailservice.data.EmailData;
import com.example.emailservice.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/emails")
@RestController
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private final EmailService emailService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmailData> read(@PathVariable(name = "id") String id) {
        EmailData emailData = emailService.read(id);
        if (emailData != null) {
            return new ResponseEntity<>(emailData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create")
    public String create(@RequestBody String body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            EmailData emailData = mapper.readValue(body, EmailData.class);
            return emailService.create(emailData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
