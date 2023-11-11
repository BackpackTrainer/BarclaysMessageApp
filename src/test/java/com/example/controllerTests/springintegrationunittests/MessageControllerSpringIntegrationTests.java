package com.example.controllerTests.springintegrationunittests;

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
}
