package com.example.controllers;

import com.example.entity.Message;
import com.example.services.MessageService;
import com.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {
    MessageService messageService;

    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable Long messageId)  {
        return messageService.getMessageById(messageId);
    }

    @GetMapping("/messages")
    public Iterable<Message> getAllMessages() {
        return messageService.findAll();
    }

    @GetMapping("/messages/bySenderEmail/{senderEmail}")
    public Iterable<Message> getMessagesBySenderEmail(@PathVariable String senderEmail) {
        return messageService.findMessagesBySenderEmail(senderEmail);
    }

    @PostMapping("/addMessage")
    public Message addMessage(@RequestBody Message message) {
        return messageService.addMessage(message);
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

}
