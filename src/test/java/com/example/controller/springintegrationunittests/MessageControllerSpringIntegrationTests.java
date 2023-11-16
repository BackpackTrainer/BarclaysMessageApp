package com.example.controller.springintegrationunittests;

import com.example.controllers.MessageController;
import com.example.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MessageControllerSpringIntegrationTests {

    @Autowired
    MessageController uut;

    @MockBean
    MessageService messageService;

    @Test
    public void testGetAllMessages()  {
        uut.getAllMessages();
        verify(messageService, times(1)).findAll();
    }

    @Test
    public void testGetMessageById()  {
        Long messageId = 1L;
        uut.getMessageById(messageId);
        verify(messageService, times(1)).getMessageById(messageId);
    }

    @Test
    public void testGetMessageBySenderEmail() {
        String senderEmail = "me@here.com";

        uut.getMessagesBySenderEmail(senderEmail);
        verify(messageService, times(1)).findMessagesBySenderEmail(senderEmail);
    }
}
