package com.example.entity;

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
    private List<Message> sentMessages;
    public Person()  {}
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
        sentMessages = new ArrayList<>();
    }



}
