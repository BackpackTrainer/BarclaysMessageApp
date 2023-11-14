package com.example.service.springintegrationunittests;

import com.example.TestUtility;
import com.example.dataaccess.MessageRepository;
import com.example.entity.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MessageServiceSpringIntegrationTests {

    @Autowired
    MessageService uut;

    @MockBean
    MessageRepository messageRepository;

   @Test
   public void testFindMessageById()  {
       Long messageId = 1L;
       uut.getMessageById(messageId);

       verify(messageRepository, times(1)).findById(messageId);
   }

    @Test
    public void testGetAllMessages()  {
        uut.findAll();
        verify(messageRepository, times(1)).findAll();
    }
    @Test
    public void testReturnFromFindAll()  {
        when(messageRepository.findAll()).thenReturn(TestUtility.createMessageList());
        int expectedLength = 3;

        Iterable<Message> actualReturn = uut.findAll();
        int actualLength = TestUtility.getIterableSize(actualReturn);

        assertEquals(expectedLength, actualLength);
    }
}
