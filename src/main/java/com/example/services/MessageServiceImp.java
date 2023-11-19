package com.example.services;

import com.example.dataaccess.MessageRepository;
import com.example.dataaccess.PersonRepository;
import com.example.entity.Message;
import com.example.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class MessageServiceImp implements MessageService{
    private MessageRepository messageRepository;
    private PersonRepository personRepository;
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

    @Override
    public Message addMessage(Message message) {
        String name = message.getSender().getName();
        Optional<Person> prospectivePerson = personRepository.findByName(name);
        if(prospectivePerson.isEmpty())  {
            Person sender = personRepository.save(message.getSender());
            message.setSender(sender);
        }
        else message.setSender(prospectivePerson.get());

        return messageRepository.save(message);
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
