package com.example.backendtestwithrealhttprequest;

import com.example.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTestswithRealHttpRequest {
    ObjectMapper mapper = new ObjectMapper();

    HttpUriRequest getRequest;
    HttpResponse response;
    HttpClient httpClient = HttpClientBuilder.create().build();

    @Test
    public void testAddingAPerson() throws IOException {
        String name = "Bill";
        String email = "bill@gmail.com";
        Person testPerson = new Person(name, email);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(testPerson);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

        HttpPost request = new HttpPost("http://localhost:8080/addPerson");
        request.setEntity(entity);
        httpClient.execute(request);

        response = (HttpResponse)HttpClientBuilder.create().build().execute(request);

        assertAll("Testing the deployed app",
                ()-> assertTrue(checkIfOnList(name, email))
        );
    }

    private boolean checkIfOnList(String name, String email) throws IOException {
        boolean isOnList = false;

        getRequest = new HttpGet("http://localhost:8080/persons");
        response = (HttpResponse) HttpClientBuilder.create().build().execute(getRequest);

        Person[] persons = mapper.readValue(response.getEntity().getContent(), Person[].class);
        for(Person p : persons) {
            if (p.getName().equals(name) && p.getEmail().equals(email)) {
                isOnList = true;
            }
        }return isOnList;
    }
}
