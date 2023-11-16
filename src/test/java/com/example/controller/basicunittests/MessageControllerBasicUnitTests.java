package com.example.controller.basicunittests;

import com.example.controllers.MessageController;
import com.example.entity.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MessageControllerBasicUnitTests {
    MessageController uut;
    MessageService messageService;

    @BeforeEach
    public void setUp() {
        uut = new MessageController();
        messageService = mock(MessageService.class);
        uut.setMessageService(messageService);
    }

    @Test
    public void testCreation() {
        assertNotNull(uut);
    }

    @Test
    public void testGetAllMessages()  {
        uut.getAllMessages();
        verify(messageService, times(1)).findAll();
    }

    @Test
    public void testGetMessageById()  {
        Long id =1L;
        uut.getMessageById(id);
        verify(messageService, times(1)).getMessageById(id);
    }
    @Test
    public void testGetMessageBySenderEmail()  {
        String senderEmail = "me@here.com";

        uut.getMessagesBySenderEmail(senderEmail);
        verify(messageService, times(1)).findMessagesBySenderEmail(senderEmail);
    }


}
