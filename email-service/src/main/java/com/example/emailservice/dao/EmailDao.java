package com.example.emailservice.dao;

import com.example.emailservice.data.EmailData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDao extends MongoRepository<EmailData, String> {
}
