package com.example.controllers;

import com.example.entity.Person;
import com.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class PersonController {
    PersonService personService;

    @PostMapping("/addPerson")
    public Person addPerson(@RequestBody Person person) throws URISyntaxException  {
       return personService.addPerson(person);
    }

    @Autowired
    public void setPersonService(PersonService personService)  {
        this.personService = personService;
    }
}
