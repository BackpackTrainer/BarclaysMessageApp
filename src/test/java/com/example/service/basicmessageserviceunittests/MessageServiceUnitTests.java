package com.example.service.basicmessageserviceunittests;

import com.example.TestUtility;
import com.example.dataaccess.MessageRepository;
import com.example.entity.Message;
import com.example.services.MessageServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MessageServiceUnitTests {

private MessageServiceImp uut;
private MessageRepository messageRepository;

@BeforeEach
    public void setUp() {
    uut = new MessageServiceImp();
    messageRepository = mock(MessageRepository.class);
    uut.setMessageRepository(messageRepository);
    }

@Test
    public  void testCreation()  {
    assertNotNull(uut);
    }

@Test
    public  void testRepoCalledForFindAllMessages()  {
    uut.findAll();

    Mockito.verify(messageRepository, times(1)).findAll();
    }

    @Test
    public void testReturnFromFindAll()  {
    when(messageRepository.findAll()).thenReturn(TestUtility.createMessageList());
    int expectedLength = 3;

    Iterable<Message> actualReturn = uut.findAll();
    int actualLength = TestUtility.getIterableSize(actualReturn);

    assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testReturnFromFindMessagesBySenderEmail()  {
        String senderEmail = "fred@gmail.com";
        when(messageRepository.findMessagesBySenderEmail(senderEmail)).thenReturn(TestUtility.createMessageList());
        int expectedLength = 3;

        Iterable<Message> actualReturn = uut.findMessagesBySenderEmail(senderEmail);

        int actualLength = TestUtility.getIterableSize(actualReturn);

        assertEquals(expectedLength, actualLength);
    }
}
