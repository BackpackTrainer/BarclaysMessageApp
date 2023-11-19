package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @Id
    @GeneratedValue
    Long id;

    String content;

    @ManyToOne
    //@JsonBackReference
    private Person sender;

    public Message(String content, Person sender) {
        this.content = content;
        this.sender = sender;
    }

    public Message(String content) {
        this.content = content;
    }

    public Message()  {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }
}
