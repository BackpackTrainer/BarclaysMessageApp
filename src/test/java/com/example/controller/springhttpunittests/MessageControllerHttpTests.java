package com.example.controller.springhttpunittests;

import com.example.TestUtility;
import com.example.controllers.MessageController;
import com.example.entity.Message;
import com.example.entity.Person;
import com.example.services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc
public class MessageControllerHttpTests {

    @MockBean
    MessageService messageService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllMessages()  throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(messageService, times(1)).findAll();
    }

    @Test
    public void testGetAlMessageFromHttpRequest() throws Exception {

        when(messageService.findAll()).thenReturn(TestUtility.createMessageList());

        ResultActions resultActions = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Message[] actualMessages = mapper.readValue(contentAsString, Message[].class);

        assertEquals(3, actualMessages.length);
    }

    @Test
    public void testGetMessageByIdFromHttpRequest() throws Exception {
        Long id = 1L;
        Message testMessage = new Message("test message");
        when(messageService.getMessageById(id)).thenReturn(testMessage);

        ResultActions resultActions = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/messages/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Message actualMessage = mapper.readValue(contentAsString, Message.class);

        verify(messageService, times(1)).getMessageById(id);
    }

    @Test
    public void testGetMessagesBySenderEmailFromHttpRequest() throws Exception {
        String senderEmail = "fred@gmail.com";
        ArrayList<Message> senderMessages = new ArrayList<>();
        Message testMessage = new Message("test message");
        Message testMessage2 = new Message("another test message");
        senderMessages.add(testMessage2);
        senderMessages.add(testMessage);
        when(messageService.findMessagesBySenderEmail(senderEmail)).thenReturn(senderMessages);

        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/messages/bySenderEmail/" + senderEmail)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Message[] actualMessages = mapper.readValue(contentAsString, Message[].class);

        verify(messageService, times(1)).findMessagesBySenderEmail(senderEmail);
        assertAll("testing the message contents",
                () -> assertTrue(testMessage.getContent().equals(actualMessages[1].getContent())),
                () -> assertTrue(testMessage2.getContent().equals(actualMessages[0].getContent()))
        );
    }

    @Test
    public void testAddingAMessage() throws Exception {
        String name = "Dave";
        String email = "dave@gmail.com";
        Person testPerson = new Person(name, email);

        String content = "So long and Thanks for all the fish!";

        Message testMessage = new Message(content, testPerson);

        String json = mapper.writeValueAsString(testMessage);

        mockMvc.perform(MockMvcRequestBuilders.post("/addMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(messageService, times(1)).addMessage(any(Message.class));
    }
}
