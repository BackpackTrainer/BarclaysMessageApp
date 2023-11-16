package com.example.services;

import com.example.dataaccess.MessageRepository;
import com.example.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class MessageServiceImp implements MessageService{
    private MessageRepository messageRepository;
    @Override
    public Iterable<Message> findAll() {
         return messageRepository.findAll();
    }

   @Override
   public Message getMessageById(Long id) {
        Optional<Message> possibleMessage = messageRepository.findById(id);
        if(possibleMessage.isEmpty()) {
            return null;
        }
        else return possibleMessage.get();
    }

    @Override
    public Iterable<Message> findMessagesBySenderEmail(String senderEmail) {
        return messageRepository.findMessagesBySenderEmail(senderEmail);
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}
