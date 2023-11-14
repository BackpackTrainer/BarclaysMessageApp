package com.example.services;

import com.example.entity.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
    public Iterable<Message> findAll();

    public Message getMessageById(Long id);
}
