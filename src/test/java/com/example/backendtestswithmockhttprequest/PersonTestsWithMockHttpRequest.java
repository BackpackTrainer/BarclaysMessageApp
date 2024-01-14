package com.example.backendtestswithmockhttprequest;

import com.example.entity.Message;
import com.example.entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
        "spring.sql.init.mode=never"
})
public class PersonTestsWithMockHttpRequest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testAddingAPerson() throws Exception {
        int originalNumberOfPersons = getNumberOfPersons();
        String name = "Dave";
        String email = "dave@gmail.com";
        Person testPerson = new Person(name, email);

        String json = mapper.writeValueAsString(testPerson);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/addPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Person person = mapper.readValue(contentAsString, Person.class);

        assertAll("testing add a Person",
            () -> assertEquals(originalNumberOfPersons+1, getNumberOfPersons()),
            () -> assertTrue(checkIfOnList(name, email))
        );
    }

    private int getNumberOfPersons() throws Exception {
        ResultActions resultActions =this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/persons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Person[] persons = mapper.readValue(contentAsString, Person[].class);
        return persons.length;
    }

    private boolean checkIfOnList(String name, String email) throws Exception {
        boolean isOnList = false;
        ResultActions resultActions =this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/persons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Person[] persons = mapper.readValue(contentAsString, Person[].class);

        for(Person p : persons) {
            if (p.getName().equals(name) && p.getEmail().equals(email)) {
                isOnList = true;
            }
        }return isOnList;
    }
}
