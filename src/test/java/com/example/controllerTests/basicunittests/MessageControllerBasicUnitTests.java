package com.example.controllerTests.basicunittests;

import com.example.controllers.MessageController;
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
}
