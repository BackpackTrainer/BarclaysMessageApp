package com.example.backendtestwithrealhttprequest;

import com.example.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackEndTestWithRealHTTPRequest {
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllMessagesTest() throws IOException {

        HttpUriRequest request = new HttpGet("http://localhost:8080/messages");

        HttpResponse response = (HttpResponse) HttpClientBuilder.create().build().execute(request);

        Message[] messages = mapper.readValue(response.getEntity().getContent(), Message[].class);

        assertAll("Testing the deployed app",
                () -> assertEquals(4, messages.length),
                () -> assertEquals("First random message", messages[0].getContent()),
                () -> assertEquals("Second random message", messages[1].getContent()),
                () -> assertEquals("Third random message", messages[2].getContent()),
                () -> assertEquals("Fourth random message", messages[3].getContent())
        );
    }

    public void testGettingMessagesById(Long id, String content) throws Exception {
        HttpUriRequest request = new HttpGet("http://localhost:8080/messages/" + id);

        HttpResponse response = (HttpResponse)HttpClientBuilder.create().build().execute(request);

        Message message = mapper.readValue(response.getEntity().getContent(), Message.class);

        assertEquals(content, message.getContent());
    }

@Test
public void testGettingMessagesBySenderEmail() throws Exception {
    String senderEmail = "fred@gmail.com";
    int expectedLength = 2;
    HttpUriRequest request = new HttpGet("http://localhost:8080/messages/bySenderEmail/" + senderEmail);

    HttpResponse response = (HttpResponse)HttpClientBuilder.create().build().execute(request);

    Message[] messages = mapper.readValue(response.getEntity().getContent(), Message[].class);

    assertEquals(expectedLength, messages.length);
}
}
