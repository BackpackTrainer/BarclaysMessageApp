package com.example.backendtestswithmockhttprequest;

import com.example.entity.Message;
import com.example.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;


@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
        "spring.sql.init.mode=never"
})
public class MessageTestsWithMockHttpRequest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGettingAllMessages() throws Exception {
        int expectedLength = 4;

        ResultActions resultActions =this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Message[] messages = mapper.readValue(contentAsString, Message[].class);

        assertAll("Testing from a test-data.sql file",
                () -> assertEquals(expectedLength, messages.length),
                () -> assertEquals("First test message", messages[0].getContent()),
                () -> assertEquals("Second test message", messages[1].getContent()),
                () -> assertEquals("Third test message", messages[2].getContent()),
                () -> assertEquals("Fourth test message", messages[3].getContent())
        );
    }

    @ParameterizedTest
    @CsvSource({"1000, First test message", "2000, Second test message", "3000, Third test message"})
    public void testGettingMessagesById(Long id, String content) throws Exception {

        ResultActions resultActions = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/messages/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Message message = mapper.readValue(contentAsString, Message.class);

        assertEquals(content, message.getContent());
    }

    @Test
    public void testGettingMessagesBySenderEmail() throws Exception {
        int expectedLength = 2;
        String senderEmail = "fred@gmail.com";

        ResultActions resultActions =this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/messages/bySenderEmail/" + senderEmail)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Message[] messages = mapper.readValue(contentAsString, Message[].class);
        for(Message m : messages) {
            System.out.println("The senders name is " + m.getSender().getName());
        }

        assertEquals(expectedLength, messages.length);
    }

    @Test
    public void testAddingAMessage() throws Exception {
        int originalNumberOfMessages = getNumberOfMessages();
        String name = "Dave";
        String email = "dave@gmail.com";
        Person testPerson = new Person(name, email);

        String content = "And away we go!";

        Message testMessage = new Message(content, testPerson);

        String json = mapper.writeValueAsString(testMessage);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/addMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Message message = mapper.readValue(contentAsString, Message.class);

        assertAll("testing add a Person",
                () -> assertEquals(originalNumberOfMessages+1, getNumberOfMessages()),
                () -> assertTrue(checkIfOnList(testMessage))
        );
    }

    private int getNumberOfMessages() throws Exception {
        ResultActions resultActions =this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Message[] messages = mapper.readValue(contentAsString, Message[].class);
        return messages.length;
    }

    private boolean checkIfOnList(Message message) throws Exception {
        boolean isOnList = false;
        ResultActions resultActions =this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Message[] messages = mapper.readValue(contentAsString, Message[].class);

        for(Message m : messages) {
            if (m.getSender().getName().equals(message.getSender().getName()) && m.getSender().getEmail().equals(message.getSender().getEmail())) {
                isOnList = true;
            }
        }return isOnList;
    }
}