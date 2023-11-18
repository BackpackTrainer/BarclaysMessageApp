package com.example.controller.springhttpunittests;

import com.example.TestUtility;
import com.example.controllers.MessageController;
import com.example.controllers.PersonController;
import com.example.entity.Message;
import com.example.entity.Person;
import com.example.services.MessageService;
import com.example.services.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerHttpUnitTests {

    @MockBean
    PersonService personService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testAddingAPerson() throws Exception {
        String name = "Dave";
        String email = "dave@gmail.com";
        Person testPerson = new Person(name, email);

        String json = mapper.writeValueAsString(testPerson);

        mockMvc.perform(MockMvcRequestBuilders.post("/addPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(personService, times(1)).addPerson(any(Person.class));
    }
}
