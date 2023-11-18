package com.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy =  "sender")
    @JsonManagedReference
    private List<Message> sentMessages;

    public Person()  {}
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
        sentMessages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }
}
